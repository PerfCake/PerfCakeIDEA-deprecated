package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.MessageAddAction;
import org.perfcake.idea.editor.actions.MessagesEditAction;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.MessageDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class MessagesComponent extends BasicDomElementComponent<Messages> {

    private static final String TITLE = "Messages";
    private JPerfCakeIdeaRectangle messagesGui;

    public MessagesComponent(Messages domElement) {
        super((Messages) domElement.createStableCopy());

        messagesGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.MESSAGES_FOREGROUND, ColorType.MESSAGES_BACKGROUND);

        createSetActions();

        messagesGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    messagesGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        messagesGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        messagesGui.setTransferHandler(new ComponentTransferHandler("Messages", new MessageDropAction(myDomElement)));

        addMessages();
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        MessagesEditAction editAction = new MessagesEditAction(getDomElement(), getComponent());
        MessageAddAction addAction = new MessageAddAction(PerfCakeIdeaUtil.getMessagesValidationPair(getDomElement()), getComponent());

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);

        messagesGui.setActionMap(actionMap);
    }


    @Override
    public JComponent getComponent() {
        return messagesGui;
    }

    private void addMessages() {
        if(getDomElement().isValid()){
            for (Message m : getDomElement().getMessages()) {
                MessageComponent messageComponent = new MessageComponent(m);
                addComponent(messageComponent);
                messagesGui.addComponent(messageComponent.getComponent());
            }
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
