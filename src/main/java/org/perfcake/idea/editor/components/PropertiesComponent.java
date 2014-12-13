package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.gui.PropertiesGui;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.model.Properties;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class PropertiesComponent extends BasicDomElementComponent<Properties> {

    private PropertiesGui propertiesGui;

    public PropertiesComponent(Properties domElement) {
        super((Properties) domElement.createStableCopy());

        propertiesGui = new PropertiesGui();
        createSetActions();

        propertiesGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    propertiesGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }

            }
        });

        propertiesGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        propertiesGui.setTransferHandler(new ComponentTransferHandler("Properties", new PropertyDropAction(domElement)));

        addProperties();
    }

    @Override
    public JComponent getComponent() {
        return propertiesGui;
    }

    private void addProperties() {
        if (getDomElement().isValid()) {
            for (Property p : myDomElement.getProperties()) {
                PropertyComponent propertyComponent = new PropertyComponent(p);
                addComponent(propertyComponent);
                propertiesGui.addComponent(propertyComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();

        getChildren().clear();
        propertiesGui.removeAllComponents();

        addProperties();
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PropertyAddAction addAction = new PropertyAddAction(getDomElement(), getComponent());
        EditAction editAction = new EditAction("Edit Properties", getDomElement(), getComponent());

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);

        propertiesGui.setActionMap(actionMap);
    }

}
