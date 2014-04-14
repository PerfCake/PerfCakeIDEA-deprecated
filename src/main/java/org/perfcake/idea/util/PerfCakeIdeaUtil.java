package org.perfcake.idea.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.perfcake.PerfCakeConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 2.4.2014.
 */
public class PerfCakeIdeaUtil {
    /**
     * Shows user PerfCakeIDEA error
     *
     * @param project current idea project
     * @param title   error title
     * @param e       exception to show
     */
    public static void showError(Project project, String title, Throwable e) {
        Notifications.Bus.notify(new Notification("PerfCakeIDEA", title, e.getMessage(), NotificationType.ERROR), project);
    }

    /**
     * resolves module directories for storing scenarios and messages
     *
     * @param project current idea project with PerfCake modules
     * @param file    file in one of project modules for which we want to get directories
     * @return module directories map
     * @throws PerfCakeIDEAException if we could not get module of the file
     */
    public static Map<String, VirtualFile> resolveModuleDirsForFile(Project project, VirtualFile file) throws PerfCakeIDEAException {
        ProjectFileIndex projectFileIndex = ProjectFileIndex.SERVICE.getInstance(project);
        VirtualFile moduleRoot = projectFileIndex.getContentRootForFile(file);
        if (moduleRoot == null) {
            throw new PerfCakeIDEAException("Cannot resolve PerfCake standard module directories for file " + file.toString());
        }

        final VirtualFile scenariosDir = moduleRoot.findChild("Scenarios");
        final VirtualFile messagesDir = moduleRoot.findChild("Messages");

        Map<String, VirtualFile> dirs = new HashMap<>();
        dirs.put(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenariosDir);
        dirs.put(PerfCakeConst.MESSAGES_DIR_PROPERTY, messagesDir);
        return dirs;
    }
}
