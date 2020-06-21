package com.andrey4623.rainbowcsv.settings;

import com.intellij.application.options.colors.ColorAndFontOptions;
import com.intellij.application.options.colors.ColorAndFontSettingsListener;
import com.intellij.application.options.colors.OptionsPanel;
import com.intellij.application.options.colors.TextAttributesDescription;
import com.intellij.ui.ColorPanel;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class ColorSettings implements OptionsPanel {

    private JPanel panel;
    private ColorPanel colorPanel1;
    private ColorPanel colorPanel2;
    private ColorPanel colorPanel3;
    private ColorPanel colorPanel4;
    private ColorPanel colorPanel5;
    private ColorPanel colorPanel6;
    private ColorPanel colorPanel7;
    private ColorPanel colorPanel8;
    private ColorPanel colorPanel9;
    private ColorPanel colorPanel10;
    private ColorPanel commentColorPanel;

    private final ColorPanel[] colorPanels = new ColorPanel[]{
            colorPanel1, colorPanel2, colorPanel3, colorPanel4, colorPanel5, colorPanel6,
            colorPanel7, colorPanel8, colorPanel9, colorPanel10
    };

    private final TextAttributesDescription[] textAttributesDescriptions
            = new TextAttributesDescription[colorPanels.length];
    private TextAttributesDescription commentTextAttributesDescription;

    private final ColorAndFontOptions options;
    private final String group;

    public ColorSettings(ColorAndFontOptions options, String group) {
        this.options = options;
        this.group = group;

        ActionListener actionListener = (actionEvent) -> {
            for (int i = 0; i < textAttributesDescriptions.length; i++) {
                textAttributesDescriptions[i].setExternalForeground(colorPanels[i].getSelectedColor());
            }
            commentTextAttributesDescription.setExternalForeground(commentColorPanel.getSelectedColor());
        };

        for (ColorPanel colorPanel : colorPanels) {
            colorPanel.addActionListener(actionListener);
        }
        commentColorPanel.addActionListener(actionListener);
    }

    @Override
    public void addListener(ColorAndFontSettingsListener listener) {
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void updateOptionsList() {
        Arrays.stream(options.getCurrentDescriptions())
                .filter(t -> t instanceof TextAttributesDescription)
                .filter(t -> group.equals(t.getGroup()))
                .map(t -> (TextAttributesDescription) t)
                .forEach(t -> {
                            if (t.toString().endsWith("COMMENT")) {
                                commentTextAttributesDescription = t;
                            } else {
                                final int index = Integer.parseInt(t.toString().substring(t.toString().length() - 1));
                                textAttributesDescriptions[index] = t;
                            }
                        }
                );

        for (int i = 0; i < colorPanels.length; i++) {
            colorPanels[i].setSelectedColor(textAttributesDescriptions[i].getExternalForeground());
        }
        commentColorPanel.setSelectedColor(commentTextAttributesDescription.getExternalForeground());
    }

    @Override
    public Runnable showOption(String option) {
        return null;
    }

    @Override
    public void applyChangesToScheme() {
    }

    @Override
    public void selectOption(String typeToSelect) {
    }

    @Override
    public Set<String> processListOptions() {
        return Collections.emptySet();
    }
}
