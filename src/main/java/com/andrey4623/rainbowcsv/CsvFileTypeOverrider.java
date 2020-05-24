package com.andrey4623.rainbowcsv;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeOverrider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CsvFileTypeOverrider implements FileTypeOverrider {

    @Nullable
    @Override
    public FileType getOverriddenFileType(@NotNull VirtualFile file) {
        if (null != file) {
            if ("csv".equals(file.getExtension())) {
                return CsvFileType.INSTANCE;
            }
        }
        return null;
    }
}
