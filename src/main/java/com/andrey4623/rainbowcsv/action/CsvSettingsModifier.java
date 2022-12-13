package com.andrey4623.rainbowcsv.action;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.FileContentUtilCore;
import com.intellij.util.xmlb.XmlSerializerUtil;

import java.util.function.Consumer;

public interface CsvSettingsModifier {

    default void modifySettingsAndReparse(VirtualFile currentFile, Consumer<CsvSettings> settingsConsumer) {
        CsvSettings fileSettings = currentFile.getUserData(CsvSettings.CSV_SETTINGS_DATA_KEY);
        if (fileSettings == null) {
            fileSettings = XmlSerializerUtil.createCopy(CsvSettings.getInstance());
        }
        settingsConsumer.accept(fileSettings);
        currentFile.putUserData(CsvSettings.CSV_SETTINGS_DATA_KEY, fileSettings);

        FileContentUtilCore.reparseFiles(currentFile);
    }
}
