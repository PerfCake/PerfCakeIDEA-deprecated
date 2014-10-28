package org.perfcake.idea.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.PerfCakeConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 2.4.2014.
 */
public class PerfCakeIdeaUtil {

    /**
     * resolves module directories for storing scenarios and messages
     *
     * @param project current idea project with PerfCake modules
     * @param file    file in one of project modules for which we want to get directories
     * @return map of scenarios and messages directories with keys from PerfCakeConst class
     * @throws PerfCakeIDEAException if we could not get module of the file
     */
    @NotNull
    public static Map<String, VirtualFile> resolveModuleDirsForFile(@NotNull Project project, @NotNull VirtualFile file) throws PerfCakeIDEAException {
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
