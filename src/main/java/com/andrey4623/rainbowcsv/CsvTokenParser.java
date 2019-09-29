package com.andrey4623.rainbowcsv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CsvTokenParser {

    private static final char COMMA = ',';
    private static final char QUOTE = '"';

    public static List<List<TextRange>> parseCsv(String text) {
        List<List<TextRange>> result = new ArrayList<>();

        final int newLineLength;
        if (text.contains("\r\n")) {
            newLineLength = 2;
        } else {
            newLineLength = 1;
        }

        final List<String> lines = getLines(text);

        int offset = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                offset += newLineLength;
                continue;
            }

            List<TextRange> textRanges = new ArrayList<>();

            boolean inside = false;
            boolean newToken = true;
            int left = 0;
            for (int i = 0; i < line.length(); i++) {
                final char c = line.charAt(i);

                if (c == QUOTE) {
                    inside = !inside;
                }

                if (newToken) {
                    left = i;
                    newToken = false;
                }

                if (!inside && c == COMMA) {
                    textRanges.add(new TextRange(offset + left, offset + i, i != left));
                    newToken = true;
                }
            }

            if (newToken) {
                left = line.length();
            }

            textRanges.add(new TextRange(offset + left, offset + line.length(), true));

            result.add(Collections.unmodifiableList(textRanges));

            offset = offset + line.length() + newLineLength;
        }

        return Collections.unmodifiableList(result);
    }

    private static List<String> getLines(String text) {
        List<String> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        boolean inside = false;
        for (int i = 0; i < text.length(); i++) {
            final char c = text.charAt(i);

            if (c == '\r') {
                continue;
            }

            if (!inside && c == '\n') {
                result.add(sb.toString());
                sb.setLength(0);
                continue;
            }

            if (c == QUOTE) {
                inside = !inside;
            }

            sb.append(c);
        }

        if (sb.length() > 0) {
            result.add(sb.toString());
        }

        return result;
    }

    public static class TextRange {
        private final int startOffset;
        private final int endOffset;
        private final boolean highlight;

        public TextRange(int startOffset, int endOffset, boolean highlight) {
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.highlight = highlight;
        }

        public int getStartOffset() {
            return startOffset;
        }

        public int getEndOffset() {
            return endOffset;
        }

        public boolean isHighlight() {
            return highlight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextRange textRange = (TextRange) o;
            return startOffset == textRange.startOffset &&
                    endOffset == textRange.endOffset &&
                    highlight == textRange.highlight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startOffset, endOffset, highlight);
        }
    }
}
