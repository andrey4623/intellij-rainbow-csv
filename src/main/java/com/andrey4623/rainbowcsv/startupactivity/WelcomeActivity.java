package com.andrey4623.rainbowcsv.startupactivity;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.andrey4623.rainbowcsv.RainbowCsvHelper;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;

public class WelcomeActivity implements StartupActivity {

    @Override
    public void runActivity(@NotNull Project project) {
        if (CsvSettings.getInstance().isWelcomeNotifyShowed()) {
            return;
        }

        NotificationGroup notificationGroup = new NotificationGroup(
                "RainbowCSV", NotificationDisplayType.STICKY_BALLOON, true
        );

        NotificationListener.Adapter notificationListener = new NotificationListener.Adapter() {
            @Override
            protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent e) {
                if (!project.isDisposed()) {
                    ShowSettingsUtil.getInstance().showSettingsDialog(project, RainbowCsvHelper.OPTIONS_NAME);
                }
            }
        };

        Notification notification = notificationGroup.createNotification(
                "Rainbow CSV",
                "You can edit Rainbow CSV settings in " +
                        "<a href=\"#\">Settings > Editor > General > Rainbow CSV</a>",
                NotificationType.INFORMATION,
                notificationListener
        );

        Notifications.Bus.notify(notification);
        CsvSettings.getInstance().setWelcomeNotifyShowed(true);
    }
}
