package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class RainbowHighlightVisitor implements HighlightVisitor {

    private HighlightInfoHolder holder;

    private TextAttributes[] columnTextAttributes = new TextAttributes[0];
    private TextAttributes commentLineTextAttributes;

    private static boolean isEnabled() {
        return CsvSettings.getInstance().isEnabled();
    }

    private static TextRange convertTextRange(CsvTokenParser.TextRange textRange) {
        return new TextRange(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public boolean suitableForFile(@NotNull PsiFile file) {
        return CsvFileType.INSTANCE.equals(file.getFileType());
    }

    @Override
    public void visit(@NotNull PsiElement element) {
        if (!(element instanceof CsvFile)) {
            return;
        }
        final CsvSettings fileCsvSettings = ((CsvFile) element).getFileCsvSettings();

        if (!isEnabled()) {
            return;
        }

        final String csvFileContent = element.getText();
        if (csvFileContent != null) {
            List<List<CsvTokenParser.TextRange>> lines = CsvTokenParser.parseCsv(fileCsvSettings, csvFileContent);
            for (List<CsvTokenParser.TextRange> line : lines) {
                for (int i = 0; i < line.size(); i++) {
                    final CsvTokenParser.TextRange textRange = line.get(i);
                    if (!textRange.isHighlight()) {
                        continue;
                    }

                    final TextAttributes textAttributes;
                    if (textRange.isComment()) {
                        textAttributes = commentLineTextAttributes;
                    } else {
                        textAttributes = columnTextAttributes[i % columnTextAttributes.length];
                    }

                    holder.add(
                            HighlightInfo.newHighlightInfo(HighlightInfoType.TODO)
                                    .range(convertTextRange(textRange))
                                    .textAttributes(textAttributes)
                                    .create()
                    );
                }
            }
        }
    }

    @Override
    public boolean analyze(
            @NotNull PsiFile file,
            boolean updateWholeFile,
            @NotNull HighlightInfoHolder holder,
            @NotNull Runnable action
    ) {
        this.holder = holder;

        columnTextAttributes = Arrays.stream(RainbowCsvHelper.TEXT_ATTRIBUTES_KEYS)
                .map(t -> this.holder.getColorsScheme().getAttributes(t))
                .toArray(TextAttributes[]::new);
        commentLineTextAttributes = this.holder.getColorsScheme().getAttributes(
                RainbowCsvHelper.COMMENT_LINE_TEXT_ATTRIBUTES_KEYS
        );

        action.run();

        return true;
    }

    @Override
    public @NotNull HighlightVisitor clone() {
        return new RainbowHighlightVisitor();
    }
}
