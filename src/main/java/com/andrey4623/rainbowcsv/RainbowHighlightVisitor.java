package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RainbowHighlightVisitor implements HighlightVisitor {

    private HighlightInfoHolder holder;

    private TextAttributes[] columnTextAttributes = new TextAttributes[0];
    private TextAttributes commentLineTextAttributes;

    @Override
    public boolean suitableForFile(@NotNull PsiFile file) {
        return CsvFileType.INSTANCE.equals(file.getFileType());
    }

    @Override
    public void visit(@NotNull PsiElement element) {
        if (!(element instanceof CsvFile)) {
            return;
        }

        if (!isEnabled()) {
            return;
        }

        final String csvFileContent = element.getText();
        if (csvFileContent != null) {
            List<List<CsvTokenParser.TextRange>> lines = CsvTokenParser.parseCsv(csvFileContent);
            for (List<CsvTokenParser.TextRange> line : lines) {
                for (int i = 0; i < line.size(); i++) {
                    final CsvTokenParser.TextRange textRange = line.get(i);
                    if (!textRange.isHighlight()) {
                        continue;
                    }

                    final TextAttributes textAttributes;
                    if (textRange.isComment()) {
                        textAttributes = commentLineTextAttributes;
                    } else {
                        textAttributes = columnTextAttributes[i % columnTextAttributes.length];
                    }

                    holder.add(
                            HighlightInfo.newHighlightInfo(HighlightInfoType.TODO)
                                    .range(convertTextRange(textRange))
                                    .textAttributes(textAttributes)
                                    .create()
                    );
                }
            }
        }
    }

    private static boolean isEnabled() {
        return CsvSettings.getInstance().isEnabled();
    }

    private static TextRange convertTextRange(CsvTokenParser.TextRange textRange) {
        return new TextRange(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public boolean analyze(
            @NotNull PsiFile file,
            boolean updateWholeFile,
            @NotNull HighlightInfoHolder holder,
            @NotNull Runnable action
    ) {
        this.holder = holder;
        
        columnTextAttributes = new TextAttributes[CsvSettings.getInstance().getTextAttributesSize()];
        for (int i = 0; i < CsvSettings.getInstance().getTextAttributesSize(); i++)
        {
            columnTextAttributes[i] = this.holder.getColorsScheme().getAttributes(RainbowCsvHelper.TEXT_ATTRIBUTES_KEYS[i]);
        }
        
        commentLineTextAttributes = this.holder.getColorsScheme().getAttributes(
                RainbowCsvHelper.COMMENT_LINE_TEXT_ATTRIBUTES_KEYS
        );

        action.run();

        return true;
    }

    @Override
    public @NotNull HighlightVisitor clone() {
        return new RainbowHighlightVisitor();
    }
}
