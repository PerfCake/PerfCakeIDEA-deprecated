package org.perfcake.idea.oldmodel;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class MessagesModel extends AbstractScenarioModel {
    public static final String MESSAGE_PROPERTY = "message";
    public static final String MESSAGES_PROPERTY = "messages";

    private static final Logger LOG = Logger.getInstance(MessagesModel.class);

    private Scenario.Messages messages;

    public MessagesModel(Scenario.Messages messages) {
        this.messages = messages;
    }

    public Scenario.Messages getMessages() {
        return messages;
    }

    public void setMessages(Scenario.Messages messages) {
        Scenario.Messages old = this.messages;
        this.messages = messages;
        fireChangeEvent(MESSAGES_PROPERTY, old, messages);
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
    public void addMessage(@NotNull Scenario.Messages.Message message) {
        messages.getMessage().add(message);
        fireChangeEvent(MESSAGE_PROPERTY, null, message);
    }

    /**
     * Deletes message object from this oldmodel. Message object shoul be present in this oldmodel.
     *
     * @param message message to delete
     */
    public void deleteMessage(@NotNull Scenario.Messages.Message message) {
        boolean success = messages.getMessage().remove(message);
        if (!success) {
            LOG.error(getClass().getName() + ": Message " + message.getUri() + " was not found in PerfCake JAXB oldmodel");
            return;
        }
        fireChangeEvent(MESSAGE_PROPERTY, message, null);
    }
}
