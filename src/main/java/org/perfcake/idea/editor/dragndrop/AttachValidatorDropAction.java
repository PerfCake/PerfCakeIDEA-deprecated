package org.perfcake.idea.editor.dragndrop;

import org.perfcake.idea.model.Message;

/**
 * Created by miron on 21. 12. 2014.
 */
public class AttachValidatorDropAction implements DropAction {

    private Message message;

    public AttachValidatorDropAction(Message message) {
        this.message = message;
    }

    @Override
    public boolean invoke(String elementToAdd) {
        if (message.isValid()) {

        }
        return false;
    }
}
