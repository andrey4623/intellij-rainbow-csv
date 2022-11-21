package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CsvFile extends PsiFileBase {

    private CsvSettings fileCsvSettings;

    public CsvFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CsvLanguage.INSTANCE);
        fileCsvSettings = XmlSerializerUtil.createCopy(CsvSettings.getInstance()); // Copy default settings
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CsvFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "CSV file";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }

    public CsvSettings getFileCsvSettings() {
        return fileCsvSettings;
    }

    public void setFileCsvSettings(CsvSettings newSettings){
        fileCsvSettings = newSettings;
    }
}
