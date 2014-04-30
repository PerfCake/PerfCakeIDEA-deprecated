package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class MessagesModel extends AbstractScenarioModel {
    public static final String MESSAGE_PROPERTY = "message";
    private static final Logger LOG = Logger.getInstance(MessagesModel.class);
    private Scenario.Messages messages;

    public MessagesModel(Scenario.Messages messages) {
        this.messages = messages;
    }

    public Scenario.Messages getMessages() {
        return messages;
    }

    /**
     * Adds new message at a specified position given by the index. Existent messages in this position will be moved after this message.
     *
     * @param index   at which should the message be placed
     * @param message to add
     */
    public void addMessage(int index, @NotNull Scenario.Messages.Message message) {
        messages.getMessage().add(index, message);
        fireChangeEvent(MESSAGE_PROPERTY, null, message);
    }

    /**
     * Adds new message to the end.
     *
     * @param message to add
     */
    public void addProperty(@NotNull Scenario.Messages.Message message) {
        messages.getMessage().add(message);
        fireChangeEvent(MESSAGE_PROPERTY, null, message);
    }

    /**
     * Deletes message object from this model.
     *
     * @param message message to delete
     */
    public void deleteProperty(@NotNull Scenario.Messages.Message message) {
        boolean success = messages.getMessage().remove(message);
        if (!success) {
            LOG.error("Message " + message.getUri() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(MESSAGE_PROPERTY, message, null);
    }
}
