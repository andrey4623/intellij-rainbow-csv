package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.Delimiter;
import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.intellij.util.xmlb.annotations.OptionTag;

public class CsvSettingsData {

    public boolean enabled = true;

    public boolean welcomeNotifyShowed = false;

    @OptionTag(converter = DelimiterConverter.class)
    public Delimiter delimiter = Delimiter.COMMA;

    @OptionTag(converter = EscapeCharacterConverter.class)
    public EscapeCharacter escapeCharacter = EscapeCharacter.DOUBLE_QUOTE;

    public CsvSettingsData() {
    }
}
