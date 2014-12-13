package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.model.Sender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class SenderComponent extends BasicDomElementComponent<Sender> {

    private JPerfCakeIdeaRectangle senderGui;

    public SenderComponent(Sender domElement) {
        super((Sender) domElement.createStableCopy());

        senderGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.SENDER_FOREGROUND, ColorType.SENDER_BACKGROUND);

        createSetActions();

        senderGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    senderGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        senderGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        senderGui.setTransferHandler(new ComponentTransferHandler("Properties", new PropertyDropAction(myDomElement)));
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PropertyAddAction addAction = new PropertyAddAction(getDomElement(), getComponent());

        EditAction editAction = new EditAction("Edit Sender", getDomElement(), getComponent());

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);

        senderGui.setActionMap(actionMap);
    }

    @Override
    public JComponent getComponent() {
        return senderGui;
    }

    @Override
    public void reset() {
        super.reset();

        getChildren().clear();
        senderGui.removeAllComponents();

        if (getDomElement().isValid()) {
            senderGui.setTitle(getDomElement().getClazz().getStringValue());
            addProperties();
        } else {
            senderGui.setTitle("INVALID");
        }
    }

    private void addProperties() {
        if(getDomElement().isValid()){
            for (Property p : myDomElement.getProperties()) {
                PropertyComponent propertyComponent = new PropertyComponent(p);
                addComponent(propertyComponent);
                senderGui.addComponent(propertyComponent.getComponent());
            }
        }
    }
}
