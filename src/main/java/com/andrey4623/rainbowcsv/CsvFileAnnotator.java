package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
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

    private static final List<TextAttributes> TEXT_ATTRIBUTES = COLORS.stream()
            .map(CsvFileAnnotator::getTextAttributes)
            .collect(Collectors.toList());

    @NotNull
    private static TextAttributes getTextAttributes(Color color) {
        return new TextAttributes(color, null, null, null, 0);
    }

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CsvFile) {
            if (!isEnabled()) {
                return;
            }
            final String csvFileContent = element.getText();
            if (csvFileContent != null) {
                List<List<CsvTokenParser.TextRange>> lines = CsvTokenParser.parseCsv(csvFileContent);
                for (List<CsvTokenParser.TextRange> line : lines) {
                    for (int i = 0; i < line.size(); i++) {
                        final CsvTokenParser.TextRange textRange = line.get(i);
                        final int color = i % TEXT_ATTRIBUTES.size();
                        final TextAttributes textAttributes = TEXT_ATTRIBUTES.get(color);

                        if (textRange.isHighlight()) {
                            holder.createInfoAnnotation(convertTextRange(textRange), null)
                                    .setEnforcedTextAttributes(textAttributes);
                        }
                    }
                }
            }
        }
    }

    private static boolean isEnabled() {
        return CsvSettings.getInstance().isEnabled();
    }

    private static TextRange convertTextRange(CsvTokenParser.TextRange textRange) {
        return new TextRange(textRange.getStartOffset(), textRange.getEndOffset());
    }
}
