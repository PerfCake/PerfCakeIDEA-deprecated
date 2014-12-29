package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.actions.*;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaEnablingRectangle;
import org.perfcake.idea.model.Destination;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 21.10.2014.
 */
public class DestinationComponent extends BasicDomElementComponent<Destination> {

    private JPerfCakeIdeaEnablingRectangle destinationGui;

    public DestinationComponent(Destination domElement) {
        super((Destination) domElement.createStableCopy());

        destinationGui = new JPerfCakeIdeaEnablingRectangle(getDomElement().getClazz().getStringValue(), getDomElement().getEnabled().getValue(), ColorType.DESTINATION_FOREGROUND, ColorType.DESTINATION_BACKGROUND);
        createSetActions();
        destinationGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    destinationGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        destinationGui.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("on") && getDomElement().isValid()) {
                    String commandName = evt.getNewValue().equals(Boolean.TRUE) ? "Enable Destination" : "Disable Destination";
                    new WriteCommandAction(getDomElement().getModule().getProject(), commandName, getDomElement().getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            getDomElement().getEnabled().setValue((Boolean) evt.getNewValue());
                        }
                    }.execute();
                }
            }
        });
        destinationGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        destinationGui.setTransferHandler(new ComponentTransferHandler("Properties", new PropertyDropAction(getDomElement())));
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PeriodAddAction addAction = new PeriodAddAction(getDomElement(), getComponent());
        PropertyAddAction addAction2 = new PropertyAddAction(getDomElement(), getComponent());
        SwitchEnabledAction enabledAction = new SwitchEnabledAction(destinationGui, "Destination");
        EditAction editAction = new EditAction("Edit Destination", getDomElement(), getComponent());

        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(getDomElement());
        DeleteAction deleteAction = new DeleteAction("Delete Destination", destinationList, getComponent());

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.ADD2, addAction2);
        actionMap.put(ActionType.ADD3, enabledAction);
        actionMap.put(ActionType.EDIT, editAction);
        actionMap.put(ActionType.DELETE, deleteAction);

        destinationGui.setActionMap(actionMap);
    }

    @Override
    public JComponent getComponent() {
        return destinationGui;
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            destinationGui.setTitle(getDomElement().getClazz().getStringValue());
        }
    }
}
