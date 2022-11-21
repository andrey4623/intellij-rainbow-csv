package com.andrey4623.rainbowcsv.action;

import com.andrey4623.rainbowcsv.CsvFile;
import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;

import java.util.function.Consumer;

public interface CsvSettingsModifier {

    default void modifySettingsAndReparse(CsvFile currentFile, Consumer<CsvSettings> settingsConsumer) {
        CsvSettings fileSettings = currentFile.getFileCsvSettings();
        settingsConsumer.accept(fileSettings);
        currentFile.setFileCsvSettings(fileSettings);

        DaemonCodeAnalyzer.getInstance(currentFile.getProject()).restart(currentFile);
    }
}
