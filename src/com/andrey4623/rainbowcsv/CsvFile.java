package com.andrey4623.rainbowcsv;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;

public class CsvFile extends PsiFileBase {

    public CsvFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CsvLanguage.INSTANCE);
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
}
