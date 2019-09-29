package com.andrey4623.rainbowcsv;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvTokenParserTest {

    @Test
    void testEmptyString() {
        assertEquals(Collections.emptyList(), CsvTokenParser.parseCsv(""));
    }

    @Test
    void testParseCsv() {
        List<List<CsvTokenParser.TextRange>> expected = Arrays.asList(
                Arrays.asList(
                        new CsvTokenParser.TextRange(0, 2, true),
                        new CsvTokenParser.TextRange(3, 3, false),
                        new CsvTokenParser.TextRange(4, 5, true),
                        new CsvTokenParser.TextRange(6, 11, true),
                        new CsvTokenParser.TextRange(12, 19, true),
                        new CsvTokenParser.TextRange(20, 30, true),
                        new CsvTokenParser.TextRange(31, 34, true),
                        new CsvTokenParser.TextRange(35, 35, true)
                ),
                Arrays.asList(
                        new CsvTokenParser.TextRange(36, 40, true),
                        new CsvTokenParser.TextRange(41, 45, true),
                        new CsvTokenParser.TextRange(46, 53, true),
                        new CsvTokenParser.TextRange(54, 57, true),
                        new CsvTokenParser.TextRange(58, 61, true)
                ),
                Arrays.asList(
                        new CsvTokenParser.TextRange(64, 67, true)
                )
        );

        List<List<CsvTokenParser.TextRange>> actual = CsvTokenParser.parseCsv("  ,, , abc , \"def\" , \"a\n" +
                "\n" +
                "b\n" +
                "\n" +
                "c\",def,\n" +
                "abc , def,\"\"\"abc\",def,abc\n" +
                "\n" +
                "\n" +
                "abc\n");

        assertEquals(expected, actual);
    }
}
