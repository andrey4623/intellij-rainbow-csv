package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.RainbowCsvHelper;
import com.intellij.application.options.colors.ColorAndFontOptions;
import com.intellij.application.options.colors.ColorAndFontPanelFactory;
import com.intellij.application.options.colors.NewColorAndFontPanel;
import com.intellij.application.options.colors.PreviewPanel;
import com.intellij.application.options.colors.SchemesPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class RainbowCsvColorAndFontPanelFactory implements ColorAndFontPanelFactory {

    @Override
    public @NotNull NewColorAndFontPanel createPanel(@NotNull ColorAndFontOptions options) {
        ColorSettings colorSettings = new ColorSettings(options, RainbowCsvHelper.GROUP);

        return new NewColorAndFontPanel(
                new SchemesPanel(options),
                colorSettings,
                new PreviewPanel.Empty(),
                RainbowCsvHelper.GROUP,
                null,
                null
        );
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getPanelDisplayName() {
        return RainbowCsvHelper.COLOR_OPTIONS_NAME;
    }
}
