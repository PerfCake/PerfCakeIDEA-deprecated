package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.Message;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class MessageComponent extends BasicDomElementComponent<Message> {

    private JTitledRoundedRectangle messageGui;

    public MessageComponent(Message domElement) {
        super(domElement);

        messageGui = new JTitledRoundedRectangle(domElement.getUri().getStringValue());
    }

    @Override
    public JComponent getComponent() {
        return messageGui;
    }

    @Override
    public void reset() {
        super.reset();
        messageGui.setTitle(myDomElement.getUri().getStringValue());
    }
}
