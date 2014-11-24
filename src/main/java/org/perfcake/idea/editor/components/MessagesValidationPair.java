package org.perfcake.idea.editor.components;

import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.model.Validation;

/**
 * Created by miron on 19. 11. 2014.
 */
public class MessagesValidationPair {
    private Messages messages;
    private Validation validation;

    public MessagesValidationPair(@NotNull Messages messages, @NotNull Validation validation) {
        this.messages = messages;
        this.validation = validation;
    }

    public Messages getMessages() {
        return messages;
    }

    public Validation getValidation() {
        return validation;
    }
}
