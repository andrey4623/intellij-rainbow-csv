package com.andrey4623.rainbowcsv;

import com.intellij.lexer.FlexAdapter;

public class CsvLexerAdapter extends FlexAdapter {

    public CsvLexerAdapter() {
        super(new CsvLexer(null));
    }
}
