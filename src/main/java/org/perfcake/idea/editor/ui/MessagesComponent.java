package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class MessagesComponent extends BasicDomElementComponent<Messages> {

    private static final String TITLE = "Messages";
    private JPerfCakeIdeaRectangle messagesGui;

    public MessagesComponent(Messages domElement) {
        super(domElement);

        messagesGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.MESSAGES_FOREGROUND, ColorType.MESSAGES_BACKGROUND);

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
