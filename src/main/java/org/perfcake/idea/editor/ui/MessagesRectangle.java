package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.MessageModel;
import org.perfcake.idea.editor.model.MessagesModel;
import org.perfcake.model.Scenario;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class MessagesRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private static final String TITLE = "Messages";
    private static final Logger LOG = Logger.getInstance(MessagesRectangle.class);
    private MessagesModel model;

    public MessagesRectangle(MessagesModel model) {
        super(TITLE);
        this.model = model;
        model.addPropertyChangeListener(this);
        if (model.getMessages() != null) {
            for (Scenario.Messages.Message m : model.getMessages().getMessage()) {
                MessageModel messageModel = new MessageModel(m);
                MessageRectangle messageRectangle = new MessageRectangle(messageModel);
                add(messageRectangle);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MessagesModel.MESSAGE_PROPERTY) {
            Scenario.Messages.Message oldValue = (Scenario.Messages.Message) evt.getOldValue();
            Scenario.Messages.Message newValue = (Scenario.Messages.Message) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                MessageRectangle messageRectangle = new MessageRectangle(new MessageModel(newValue));
                add(messageRectangle, BorderLayout.WEST);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = getComponents();
                    for (Component c : components) {
                        if (c instanceof MessageRectangle) {
                            if (((MessageRectangle) c).getModel().getMessage() == oldValue) {
                                remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("PropertyRectangle with property " + oldValue.toString() + " was not found in PropertiesRectangle");
                }
            }
        }
    }
}
