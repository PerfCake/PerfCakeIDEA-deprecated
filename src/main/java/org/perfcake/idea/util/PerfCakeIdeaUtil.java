package org.perfcake.idea.util;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.model.Validation;

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

    public static <T extends DomElement> T runCreateMockCopy(final T domElement){
        if(domElement.isValid()) {
            return (T) new WriteAction() {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    result.setResult(domElement.createMockCopy(false));
                }
            }.execute().getResultObject();
        }
        throw new RuntimeException("Invalid XML. Cannot create mockCopy.");
    }

    public static MessageValidationPair getMessageValidationPair(Message message){
        if (message.isValid() && message.getParentOfType(Scenario.class, true) != null) {
            Scenario scenario = message.getParentOfType(Scenario.class, true);
            if (scenario.isValid() && scenario.getValidation().isValid()) {
                final Validation validation = scenario.getValidation();
                return new MessageValidationPair(message, validation);
            }
        }
        throw new RuntimeException("Scenario xml is not valid.");
    }

    public static MessagesValidationPair getMessagesValidationPair(Messages messages){
        if (messages.isValid() && messages.getParentOfType(Scenario.class, true) != null) {
            Scenario scenario = messages.getParentOfType(Scenario.class, true);
            if (scenario.isValid() && scenario.getValidation().isValid()) {
                final Validation validation = scenario.getValidation();
                return new MessagesValidationPair(messages, validation);
            }
        }
        throw new RuntimeException("Scenario xml is not valid.");
    }
}
