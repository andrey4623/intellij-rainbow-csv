package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvTokenParserTest {

    @Test
    void testEmptyString() {
        assertEquals(Collections.emptyList(), CsvTokenParser.parseCsv(CsvSettings.getInstance(),""));
    }

    @Test
    void testParseCsv() {
        List<List<CsvTokenParser.TextRange>> expected = Arrays.asList(
                Arrays.asList(
                        new CsvTokenParser.TextRange(0, 2, true, false),
                        new CsvTokenParser.TextRange(3, 3, false, false),
                        new CsvTokenParser.TextRange(4, 5, true, false),
                        new CsvTokenParser.TextRange(6, 11, true, false),
                        new CsvTokenParser.TextRange(12, 19, true, false),
                        new CsvTokenParser.TextRange(20, 30, true, false),
                        new CsvTokenParser.TextRange(31, 34, true, false),
                        new CsvTokenParser.TextRange(35, 35, true, false)
                ),
                Arrays.asList(
                        new CsvTokenParser.TextRange(36, 40, true, false),
                        new CsvTokenParser.TextRange(41, 45, true, false),
                        new CsvTokenParser.TextRange(46, 53, true, false),
                        new CsvTokenParser.TextRange(54, 57, true, false),
                        new CsvTokenParser.TextRange(58, 61, true, false)
                ),
                Arrays.asList(
                        new CsvTokenParser.TextRange(64, 67, true, false)
                )
        );

        List<List<CsvTokenParser.TextRange>> actual = CsvTokenParser.parseCsv(CsvSettings.getInstance(),"  ,, , abc , \"def\" , \"a\n" +
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
