package org.perfcake.idea.editor.ui.components;

import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.MessageModel;
import org.perfcake.idea.model.RunModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class MessageRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private MessageModel model;

    public MessageRectangle(MessageModel model) {
        super(model.getMessage().getUri());
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public MessageModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MessageModel.URI_PROPERTY)) {
            model.setUri((String) evt.getNewValue());
        }
        if(evt.getPropertyName().equals(MessageModel.MESSAGE_PROPERTY)){
            updateRectangle();
        }
    }

    private void updateRectangle() {
        model.setUri((String) model.getMessage().getUri());
    }
}
