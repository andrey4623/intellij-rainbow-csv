package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.Delimiter;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelimiterConverter extends Converter<Delimiter> {

    @Nullable
    @Override
    public Delimiter fromString(@NotNull String value) {
        return Delimiter.getDelimiter(value.charAt(0));
    }

    @Override
    public @Nullable String toString(@NotNull Delimiter value) {
        return String.valueOf(value.getDelimiter());
    }
}
