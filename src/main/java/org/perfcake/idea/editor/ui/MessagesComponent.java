package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.editor.ColorComponents;
import org.perfcake.idea.editor.swing.JTitledRoundedRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.10.2014.
 */
public class MessagesComponent extends BasicDomElementComponent<Messages> {

    private static final String TITLE = "Messages";
    private JTitledRoundedRectangle messagesGui;

    public MessagesComponent(Messages domElement) {
        super(domElement);

        messagesGui = new JTitledRoundedRectangle(TITLE);

        addMessages();
    }


    @Override
    public JComponent getComponent() {
        return messagesGui;
    }

    private void addMessages() {
        for (Message m : myDomElement.getMessages()) {
            MessageComponent messageComponent = new MessageComponent(m);
            addComponent(messageComponent);
            messagesGui.addComponent(messageComponent.getComponent());
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        messagesGui.removeAllComponents();

        addMessages();
    }
}
