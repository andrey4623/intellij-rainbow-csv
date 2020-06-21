package com.andrey4623.rainbowcsv;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorAndFontDescriptorsProvider;
import com.intellij.openapi.options.colors.ColorDescriptor;
import org.jetbrains.annotations.Nls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RainbowColorAndFontDescriptorsProvider implements ColorAndFontDescriptorsProvider {

    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {

        List<TextAttributesKey> list = new ArrayList<>(
                Arrays.asList(RainbowCsvHelper.TEXT_ATTRIBUTES_KEYS)
        );
        list.add(RainbowCsvHelper.COMMENT_LINE_TEXT_ATTRIBUTES_KEYS);
        return list.stream()
                .map(t -> new AttributesDescriptor(
                        t.toString(),
                        t
                ))
                .toArray(AttributesDescriptor[]::new);
    }

    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return RainbowCsvHelper.GROUP;
    }
}
