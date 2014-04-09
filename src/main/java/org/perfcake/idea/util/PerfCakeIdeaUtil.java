package org.perfcake.idea.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;

/**
 * Created by miron on 2.4.2014.
 */
public class PerfCakeIdeaUtil {
    public static void showError(Project project, String title, Throwable e) {
        Notifications.Bus.notify(new Notification("PerfCakeIDEA", title, e.getMessage(), NotificationType.ERROR), project);
    }
}
