package com.andrey4623.rainbowcsv;

import com.intellij.openapi.editor.colors.TextAttributesKey;

public class RainbowCsvHelper {

    public static final String OPTIONS_NAME = "Rainbow CSV";
    public static final String COLOR_OPTIONS_NAME = "Rainbow CSV";
    public static final String GROUP = "RAINBOW_CSV_COLOR_GROUP";

    public static final TextAttributesKey[] TEXT_ATTRIBUTES_KEYS =
            new TextAttributesKey[] {
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_0"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_1"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_2"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_3"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_4"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_5"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_6"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_7"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_8"),
                    TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_9")
            };

    public static final TextAttributesKey COMMENT_LINE_TEXT_ATTRIBUTES_KEYS =
            TextAttributesKey.createTextAttributesKey("RAINBOW_CSV_COLOR_COMMENT");
}
