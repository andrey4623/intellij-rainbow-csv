package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EscapeCharacterConverter extends Converter<EscapeCharacter> {

    @Nullable
    @Override
    public EscapeCharacter fromString(@NotNull String value) {
        return EscapeCharacter.getEscapeCharacter(value.charAt(0));
    }

    @Override
    public @Nullable String toString(@NotNull EscapeCharacter value) {
        return String.valueOf(value.getEscapeCharacter());
    }
}
