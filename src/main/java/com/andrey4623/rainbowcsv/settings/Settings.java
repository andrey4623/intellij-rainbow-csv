package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.CsvFile;
import com.andrey4623.rainbowcsv.Delimiter;
import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.andrey4623.rainbowcsv.RainbowCsvHelper;
import com.intellij.application.options.editor.EditorOptionsProvider;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.FileContentUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Settings implements EditorOptionsProvider {

    private JPanel panel;
    private JCheckBox rainbowCSVEnabledCheckBox;
    private JComboBox delimiterComboBox;
    private JComboBox escapeCharacterComboBox;
    private JTextField commentPrefixTextField;
    private JCheckBox highlightCommentsCheckBox;

    private JTextField pageLinesInput;

    @Override
    public @NotNull String getId() {
        return "RainbowCSV.Settings";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return RainbowCsvHelper.OPTIONS_NAME;
    }

    @Override
    public @Nullable JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        CsvSettings settings = CsvSettings.getInstance();

        return this.rainbowCSVEnabledCheckBox.isSelected() != settings.isEnabled()
                || !settings.getMaxLinesToRender().equals(pageLinesInput.getText())
                || !settings.getDelimiter().equals(this.delimiterComboBox.getSelectedItem())
                || !settings.getEscapeCharacter().equals(this.escapeCharacterComboBox.getSelectedItem())
                || settings.isHighlightComments() != this.highlightCommentsCheckBox.isSelected()
                || !settings.getCommentPrefix().equals(this.commentPrefixTextField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {

        try {
            Integer.parseInt(pageLinesInput.getText());
        }
        catch (NumberFormatException exception) {
            throw new ConfigurationException("Maximum lines must be an integer");
        }

        CsvSettings settings = CsvSettings.getInstance();
        settings.setEnabled(rainbowCSVEnabledCheckBox.isSelected());
        settings.setMaxLinesToRender(Integer.parseInt(pageLinesInput.getText()));
        settings.setDelimiter((Delimiter) delimiterComboBox.getSelectedItem());
        settings.setEscapeCharacter((EscapeCharacter) escapeCharacterComboBox.getSelectedItem());
        settings.setHighlightComments(highlightCommentsCheckBox.isSelected());
        settings.setCommentPrefix(commentPrefixTextField.getText());

        reparseFiles();
    }

    private static void reparseFiles() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

        for (Project openProject : openProjects) {
            VirtualFile[] openFiles = FileEditorManager.getInstance(openProject).getOpenFiles();

            PsiManager psiManager = PsiManager.getInstance(openProject);

            List<PsiFile> list = new ArrayList<>();

            for (VirtualFile openFile : openFiles) {
                PsiFile psiFile = psiManager.findFile(openFile);
                if (psiFile != null && psiFile instanceof CsvFile) {
                    list.add(psiFile);
                }
            }
            FileContentUtil.reparseFiles(
                    openProject,
                    list.stream().map(PsiFile::getVirtualFile).collect(Collectors.toList()),
                    true
            );
        }
    }

    @Override
    public void reset() {
        CsvSettings settings = CsvSettings.getInstance();

        rainbowCSVEnabledCheckBox.setSelected(settings.isEnabled());
        pageLinesInput.setText(String.valueOf(settings.getMaxLinesToRender()));
        delimiterComboBox.setSelectedItem(settings.getDelimiter());
        escapeCharacterComboBox.setSelectedItem(settings.getEscapeCharacter());
        highlightCommentsCheckBox.setSelected(settings.isHighlightComments());
        commentPrefixTextField.setText(settings.getCommentPrefix());
    }

    protected void createUIComponents() {
        delimiterComboBox = new ComboBox(Delimiter.values());
        delimiterComboBox.setRenderer(new CustomListCellRenderer<>(Delimiter::getName));
        escapeCharacterComboBox = new ComboBox(EscapeCharacter.values());
        escapeCharacterComboBox.setRenderer(new CustomListCellRenderer<>(EscapeCharacter::getName));
    }
}
