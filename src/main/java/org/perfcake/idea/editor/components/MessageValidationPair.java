package org.perfcake.idea.editor.components;

import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Validation;

/**
 * Created by miron on 16. 11. 2014.
 */
public class MessageValidationPair {

    private Message message;
    private Validation validation;

    public MessageValidationPair(@NotNull Message message, @NotNull Validation validation) {
        this.message = message;
        this.validation = validation;
    }

    public Message getMessage() {
        return message;
    }

    public Validation getValidation() {
        return validation;
    }
}
