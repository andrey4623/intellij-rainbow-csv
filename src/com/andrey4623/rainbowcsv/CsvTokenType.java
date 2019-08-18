package com.andrey4623.rainbowcsv;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class CsvTokenType extends IElementType {

    public CsvTokenType(@NotNull String debugName) {
        super(debugName, CsvLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "CsvTokenType." + super.toString();
    }

}
