package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.actions.*;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.DestinationDropAction;
import org.perfcake.idea.editor.dragndrop.DropAction;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaEnablingRectangle;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by miron on 21.10.2014.
 */
public class ReporterComponent extends BasicDomElementComponent<Reporter> {

    private JPerfCakeIdeaEnablingRectangle reporterGui;

    public ReporterComponent(Reporter domElement) {
        super((Reporter) domElement.createStableCopy());

        reporterGui = new JPerfCakeIdeaEnablingRectangle(domElement.getClazz().getStringValue(), domElement.getEnabled().getValue(), ColorType.REPORTER_FOREGROUND, ColorType.REPORTER_BACKGROUND);
        createSetActions();
        reporterGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    reporterGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        reporterGui.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("on") && getDomElement().isValid()) {
                    String commandName = (evt.getNewValue()).equals(Boolean.TRUE) ? "Enable Reporter" : "Disable Reporter";
                    new WriteCommandAction(getDomElement().getModule().getProject(), commandName, getDomElement().getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            getDomElement().getEnabled().setValue((Boolean) evt.getNewValue());
                        }
                    }.execute();
                }
            }
        });

        //set dropping from toolbar to this component
        HashMap<String, DropAction> prefixDropActions = new HashMap<>();
        prefixDropActions.put("Destinations", new DestinationDropAction(domElement));
        prefixDropActions.put("Properties", new PropertyDropAction(domElement));
        reporterGui.setTransferHandler(new ComponentTransferHandler(prefixDropActions));


        reporterGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));
        addDestinations();
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        DestinationAddAction addAction = new DestinationAddAction(getDomElement(), getComponent());
        PropertyAddAction addAction2 = new PropertyAddAction(getDomElement(), getComponent());

        SwitchEnabledAction enabledAction = new SwitchEnabledAction(reporterGui, "Reporter");

        EditAction editAction = new EditAction("Edit Reporter", getDomElement(), getComponent());

        List<Reporter> reporterList = new ArrayList<>();
        reporterList.add(getDomElement());
        DeleteAction deleteAction = new DeleteAction("Delete Reporter", reporterList, getComponent());

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.ADD2, addAction2);
        actionMap.put(ActionType.ADD3, enabledAction);
        actionMap.put(ActionType.EDIT, editAction);
        actionMap.put(ActionType.DELETE, deleteAction);

        reporterGui.setActionMap(actionMap);
    }


    @Override
    public JComponent getComponent() {
        return reporterGui;
    }


    private void addDestinations() {

            for (Destination d : getDomElement().getDestinations()) {
                DestinationComponent destinationComponent = new DestinationComponent(d);
                addComponent(destinationComponent);
                reporterGui.addComponent(destinationComponent.getComponent());
            }

    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            reporterGui.setTitle(getDomElement().getClazz().getStringValue());
            reporterGui.setOn(getDomElement().getEnabled().getValue());
            getChildren().clear();
            reporterGui.removeAllComponents();
            addDestinations();
        }
    }
}
