package com.andrey4623.rainbowcsv;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.andrey4623.rainbowcsv.CsvTypes;
import com.intellij.psi.TokenType;

%%

%class CsvLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
KEY=[\S\ ]+

%%

<YYINITIAL> {CRLF}({CRLF}|{WHITE_SPACE})+                 { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<YYINITIAL> {KEY}                                         { yybegin(YYINITIAL); return CsvTypes.KEY; }

[^]                                                       { return TokenType.WHITE_SPACE; }
