package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.Delimiter;
import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "CsvSettings",
        storages = {@Storage("rainbow-csv.xml")}
)
public class CsvSettings implements PersistentStateComponent<CsvSettingsData> {

    public static final Key<CsvSettings> CSV_SETTINGS_DATA_KEY = new Key<>("File CSV Settings");
    private static final CsvSettings csvSettingsComponent = new CsvSettings();
    private CsvSettingsData csvSettings = new CsvSettingsData();

    public CsvSettings() {
    }

    public static CsvSettings getInstance() {
        if (ApplicationManager.getApplication() == null) {
            return csvSettingsComponent;
        }

        CsvSettings service = ApplicationManager.getApplication().getService(CsvSettings.class);
        if (service == null) {
            return csvSettingsComponent;
        }

        return service;
    }

    @Nullable
    @Override
    public CsvSettingsData getState() {
        return csvSettings;
    }

    @Override
    public void loadState(@NotNull CsvSettingsData state) {
        this.csvSettings = state;
    }

    public boolean isEnabled() {
        return getState().enabled;
    }

    public void setEnabled(boolean enabled) {
        getState().enabled = enabled;
    }

    public boolean isWelcomeNotifyShowed() {
        return getState().welcomeNotifyShowed;
    }

    public void setWelcomeNotifyShowed(boolean welcomeNotifyShowed) {
        getState().welcomeNotifyShowed = welcomeNotifyShowed;
    }

    public Delimiter getDelimiter() {
        return getState().delimiter;
    }

    public void setDelimiter(Delimiter delimiter) {
        getState().delimiter = delimiter;
    }

    public EscapeCharacter getEscapeCharacter() {
        return getState().escapeCharacter;
    }

    public void setEscapeCharacter(EscapeCharacter escapeCharacter) {
        getState().escapeCharacter = escapeCharacter;
    }

    public boolean isHighlightComments() {
        return getState().highlightComments;
    }

    public void setHighlightComments(boolean enabled) {
        getState().highlightComments = enabled;
    }

    public String getCommentPrefix() {
        return getState().commentPrefix;
    }

    public void setCommentPrefix(String commentPrefix) {
        getState().commentPrefix = commentPrefix;
    }
}
