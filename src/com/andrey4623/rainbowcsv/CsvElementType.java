package com.andrey4623.rainbowcsv;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class CsvElementType extends IElementType {

    public CsvElementType(@NotNull String debugName) {
        super(debugName, CsvLanguage.INSTANCE);
    }
}
