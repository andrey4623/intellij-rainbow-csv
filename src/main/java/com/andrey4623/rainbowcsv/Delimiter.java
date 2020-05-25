package com.andrey4623.rainbowcsv;

public class Delimiter {

    public static final Delimiter COMMA = new Delimiter(',', "Comma (,)");
    public static final Delimiter SEMICOLON = new Delimiter(';', "Semicolon (;)");
    public static final Delimiter PIPE = new Delimiter('|', "Pipe (|)");
    public static final Delimiter TAB = new Delimiter('\t', "Tab");
    public static final Delimiter COLON = new Delimiter(':', "Colon (:)");

    private final char delimiter;
    private final String name;

    private Delimiter(char delimiter, String name) {
        this.name = name;
        this.delimiter = delimiter;
    }

    public static Delimiter getDelimiter(char delimiter) {
        switch (delimiter) {
            case ',':
                return COMMA;
            case ';':
                return SEMICOLON;
            case '|':
                return PIPE;
            case '\t':
                return TAB;
            case ':':
                return COLON;
            default:
                throw new IllegalArgumentException("Unknown delimiter");
        }
    }

    public static Delimiter[] values() {
        return new Delimiter[]{COMMA, SEMICOLON, PIPE, TAB, COLON};
    }

    public String getName() {
        return name;
    }

    public char getDelimiter() {
        return delimiter;
    }
}
