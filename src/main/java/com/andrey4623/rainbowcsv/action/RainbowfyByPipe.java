package com.andrey4623.rainbowcsv.action;

import com.andrey4623.rainbowcsv.CsvFile;
import com.andrey4623.rainbowcsv.Delimiter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class RainbowfyByPipe extends AnAction implements CsvSettingsModifier {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiFile currentFile = e.getData(PlatformDataKeys.PSI_FILE);

        if (currentFile instanceof CsvFile) {
            modifySettingsAndReparse((CsvFile) currentFile, fileCsvSettings -> {
                fileCsvSettings.setDelimiter(Delimiter.PIPE);
            });
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiFile currentFile = e.getData(PlatformDataKeys.PSI_FILE);
        e.getPresentation().setEnabledAndVisible(currentFile instanceof CsvFile);
    }
}