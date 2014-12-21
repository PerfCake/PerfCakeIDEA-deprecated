package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.*;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.DropAction;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.gui.MessageGui;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by miron on 21.10.2014.
 */
public class MessageComponent extends BasicDomElementComponent<Message> {

    private JPerfCakeIdeaRectangle messageGui;

    public MessageComponent(Message domElement) {
        super((Message) domElement.createStableCopy());

        messageGui = new MessageGui(getMessageTitle(), ColorType.MESSAGE_FOREGROUND, ColorType.MESSAGE_BACKGROUND);

        createSetActions();
        messageGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    messageGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        messageGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        HashMap<String, DropAction> prefixDropActions = new HashMap<>();
        //prefixDropActions.put("Connections", new PropertyDropAction(domElement));
        prefixDropActions.put("Properties", new PropertyDropAction(domElement));
        messageGui.setTransferHandler(new ComponentTransferHandler(prefixDropActions));
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PropertyAddAction addAction = new PropertyAddAction(getDomElement(), getComponent());
        HeaderAddAction addAction2 = new HeaderAddAction(getDomElement(), getComponent());
        ValidatorAttachAction attachAction = new ValidatorAttachAction(PerfCakeIdeaUtil.getMessageValidationPair(getDomElement()), messageGui);

        MessageEditAction editAction = new MessageEditAction(PerfCakeIdeaUtil.getMessageValidationPair(getDomElement()), getComponent());

        List<Message> messageList = new ArrayList<>();
        messageList.add(getDomElement());
        DeleteAction deleteAction = new DeleteAction("Delete Message", messageList, getComponent());

        actionMap.put(ActionType.ADD, attachAction);
        actionMap.put(ActionType.ADD2, addAction);
        actionMap.put(ActionType.ADD3, addAction2);
        actionMap.put(ActionType.EDIT, editAction);
        actionMap.put(ActionType.DELETE, deleteAction);

        messageGui.setActionMap(actionMap);
    }

    @Override
    public JComponent getComponent() {
        return messageGui;
    }

    @Override
    public void reset() {
        super.reset();
        if(getDomElement().isValid()){
            messageGui.setTitle(getMessageTitle());
        }
    }

    private String getMessageTitle() {
        String title;

        title = getDomElement().getContent().getStringValue();
        if (title == null || title.isEmpty()) {
            title = getDomElement().getUri().getStringValue();
            if (title == null) title = "";
        }
        if (title.length() > 20) title = (title.substring(0, 17) + "...");

        return title;
    }
}
