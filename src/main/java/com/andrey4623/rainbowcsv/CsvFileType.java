package com.andrey4623.rainbowcsv;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CsvFileType extends LanguageFileType {

    public static final CsvFileType INSTANCE = new CsvFileType();

    private CsvFileType() {
        super(CsvLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "CSV file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "CSV file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "csv";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Text;
    }
}
