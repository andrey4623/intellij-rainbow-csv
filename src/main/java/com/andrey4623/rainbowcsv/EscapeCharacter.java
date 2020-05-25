package com.andrey4623.rainbowcsv;

public class EscapeCharacter {

    public static final EscapeCharacter DOUBLE_QUOTE = new EscapeCharacter('"', "Double Quote (\")");
    public static final EscapeCharacter BACKSLASH = new EscapeCharacter('\\', "Backslash (\\)");

    private final char escapeCharacter;
    private final String name;

    private EscapeCharacter(char escapeCharacter, String name) {
        this.escapeCharacter = escapeCharacter;
        this.name = name;
    }

    public static EscapeCharacter getEscapeCharacter(char escapeCharacter) {
        switch (escapeCharacter) {
            case '"':
                return DOUBLE_QUOTE;
            case '\\':
                return BACKSLASH;
            default:
                throw new IllegalArgumentException("Unknown escape character");
        }
    }

    public static EscapeCharacter[] values() {
        return new EscapeCharacter[]{DOUBLE_QUOTE, BACKSLASH};
    }

    public char getEscapeCharacter() {
        return escapeCharacter;
    }

    public String getName() {
        return name;
    }
}
