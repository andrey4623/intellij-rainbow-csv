package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CsvTokenParser {
    private static CsvSettings csvSettings;

    public static List<List<TextRange>> parseCsv(CsvSettings parsingSettings, String text) {
        List<List<TextRange>> result = new ArrayList<>();

        csvSettings = parsingSettings;
        final char delimiter = getDelimiter();
        final char escapeCharacter = getEscapeCharacter();
        final boolean highlightComments = isHighlightComments();
        final String commentPrefix = getCommentPrefix();

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

            if (highlightComments && line.startsWith(commentPrefix)) {
                List<TextRange> textRanges = new ArrayList<>();
                textRanges.add(new TextRange(offset, offset + line.length(), true, true));

                result.add(Collections.unmodifiableList(textRanges));

                offset = offset + line.length() + newLineLength;

                continue;
            }

            List<TextRange> textRanges = new ArrayList<>();

            boolean inside = false;
            boolean newToken = true;
            int left = 0;
            for (int i = 0; i < line.length(); i++) {
                final char c = line.charAt(i);

                if (c == escapeCharacter) {
                    inside = !inside;
                }

                if (newToken) {
                    left = i;
                    newToken = false;
                }

                if (!inside && c == delimiter) {
                    textRanges.add(new TextRange(offset + left, offset + i, i != left, false));
                    newToken = true;
                }
            }

            if (newToken) {
                left = line.length();
            }

            textRanges.add(new TextRange(offset + left, offset + line.length(), true, false));

            result.add(Collections.unmodifiableList(textRanges));

            offset = offset + line.length() + newLineLength;
        }

        return Collections.unmodifiableList(result);
    }

    private static List<String> getLines(String text) {
        List<String> result = new ArrayList<>();

        final char escapeCharacter = getEscapeCharacter();

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

            if (c == escapeCharacter) {
                inside = !inside;
            }

            sb.append(c);
        }

        if (sb.length() > 0) {
            result.add(sb.toString());
        }

        return result;
    }

    private static char getDelimiter() {
        return csvSettings.getDelimiter().getDelimiter();
    }

    private static char getEscapeCharacter() {
        return csvSettings.getEscapeCharacter().getEscapeCharacter();
    }

    private static boolean isHighlightComments() {
        return csvSettings.isHighlightComments();
    }

    private static String getCommentPrefix() {
        return csvSettings.getCommentPrefix();
    }

    public static class TextRange {
        private final int startOffset;
        private final int endOffset;
        private final boolean highlight;
        private final boolean comment;

        public TextRange(int startOffset, int endOffset, boolean highlight, boolean comment) {
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.highlight = highlight;
            this.comment = comment;
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

        public boolean isComment() {
            return comment;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextRange textRange = (TextRange) o;
            return startOffset == textRange.startOffset &&
                    endOffset == textRange.endOffset &&
                    highlight == textRange.highlight &&
                    comment == textRange.comment;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startOffset, endOffset, highlight, comment);
        }
    }
}
