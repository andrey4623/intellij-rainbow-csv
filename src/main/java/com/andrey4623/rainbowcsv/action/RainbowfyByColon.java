package com.andrey4623.rainbowcsv.action;

import com.andrey4623.rainbowcsv.Delimiter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class RainbowfyByColon extends AnAction implements CsvSettingsModifier {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        modifySettingsAndReparse(currentFile, fileCsvSettings -> {
            fileCsvSettings.setEnabled(true);
            fileCsvSettings.setDelimiter(Delimiter.COLON);
        });
    }
}