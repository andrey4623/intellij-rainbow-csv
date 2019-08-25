package com.andrey4623.rainbowcsv;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;


public class CsvFileAnnotator implements Annotator {

    private static final List<Color> COLORS = Arrays.asList(
            JBColor.RED,
            JBColor.MAGENTA,
            JBColor.GRAY,
            JBColor.GREEN,
            JBColor.BLUE,
            JBColor.BLACK,
            JBColor.ORANGE,
            JBColor.CYAN
    );

    private static final List<TextAttributesKey> TEXT_ATTRIBUTES_KEYS = COLORS.stream()
            .map(CsvFileAnnotator::createTextAttributesKey)
            .collect(Collectors.toList());

    private static TextAttributesKey createTextAttributesKey(Color color) {
        return TextAttributesKey.createTextAttributesKey(
                getExternalName(color),
                getTextAttributes(color)
        );
    }

    @NotNull
    private static String getExternalName(Color color) {
        return "COLOR" + color.toString();
    }

    @NotNull
    private static TextAttributes getTextAttributes(Color color) {
        return new TextAttributes(color, null, null, null, 0);
    }

    private static final Pattern PATTERN = Pattern.compile("(,\\s*|\\r?\\n|^)([^\",\\r\\n]+|\"(?:[^\"]|\"\")*\")?");

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CsvFile) {
            final String csvFileContent = element.getText();
            if (csvFileContent != null) {
                List<List<TextRange>> lines = parseCsv(csvFileContent);
                for (List<TextRange> line : lines) {
                    for (int i = 0; i < line.size(); i++) {
                        final TextRange token = line.get(i);
                        final int color = i % TEXT_ATTRIBUTES_KEYS.size();

                        final TextAttributesKey key = TEXT_ATTRIBUTES_KEYS.get(color);

                        holder.createInfoAnnotation(token, null).setTextAttributes(key);
                    }
                }
            }
        }
    }

    private static List<List<TextRange>> parseCsv(String text) {
        List<List<TextRange>> result = new ArrayList<>();

        final int newLineLength;
        if (text.contains("\r\n")) {
            newLineLength = 1;
        } else {
            newLineLength = 0;
        }

        final String[] lines = getLines(text);

        int offset = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                offset += 1;
                continue;
            }

            List<TextRange> textRanges = new ArrayList<>();

            final Matcher matcher = PATTERN.matcher(line);

            int lineOffset = 0;
            while (matcher.find()) {
                final String matchedText = matcher.group(2);

                if (!isNullOrEmpty(matchedText)) {
                    final int index = line.indexOf(matchedText, lineOffset);

                    TextRange r = new TextRange(offset + index, offset + index + matchedText.length());
                    textRanges.add(r);

                    lineOffset = index + matchedText.length() + 1;
                } else {
                    lineOffset += 1;
                }
            }

            offset = offset + lineOffset + newLineLength;

            result.add(textRanges);
        }

        return result;
    }

    private static String[] getLines(String text) {
        List<String> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        boolean inside = false;
        for (char c : text.toCharArray()) {
            if (c == '\r') {
                continue;
            }

            if (c == '\n') {
                if (!inside) {
                    result.add(sb.toString());
                    sb.setLength(0);
                    continue;
                }
            }

            if (c == '"') {
                inside = !inside;
            }

            sb.append(c);
        }

        if (sb.length() > 0) {
            result.add(sb.toString());
        }

        return result.toArray(new String[0]);
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
