package com.andrey4623.rainbowcsv;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CsvFile) {
            final String csvFileContent = element.getText();
            if (csvFileContent != null) {
                List<List<CsvTokenParser.TextRange>> lines = CsvTokenParser.parseCsv(csvFileContent);
                for (List<CsvTokenParser.TextRange> line : lines) {
                    for (int i = 0; i < line.size(); i++) {
                        final CsvTokenParser.TextRange textRange = line.get(i);
                        final int color = i % TEXT_ATTRIBUTES_KEYS.size();

                        TextAttributesKey key = TEXT_ATTRIBUTES_KEYS.get(color);

                        if (textRange.isHighlight()) {
                            holder.createInfoAnnotation(convertTextRange(textRange), null)
                                    .setTextAttributes(key);
                        }
                    }
                }
            }
        }
    }

    private static TextRange convertTextRange(CsvTokenParser.TextRange textRange) {
        return new TextRange(textRange.getStartOffset(), textRange.getEndOffset());
    }
}
