package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.editor.gui.PropertyGui;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private PropertyGui propertyGui;
    private PropertyDialog editDialog;

    public PropertyComponent(final Property domElement) {
        super((Property) domElement.createStableCopy());
        propertyGui = new PropertyGui(domElement.getName().getStringValue(), domElement.getValue().getStringValue());

        createSetActions();

        propertyGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    propertyGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        propertyGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));
    }

    @Override
    public JComponent getComponent() {
        return propertyGui;
    }


    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            propertyGui.setName(getDomElement().getName().getStringValue());
            propertyGui.setValue(getDomElement().getValue().getStringValue());
        } else {

        }
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        EditAction editAction = new EditAction("Edit Property", getDomElement(), getComponent());

        List<Property> propertyList = new ArrayList<>();
        propertyList.add(getDomElement());
        DeleteAction deleteAction = new DeleteAction("Delete Property", propertyList, getComponent());

        actionMap.put(ActionType.EDIT, editAction);
        actionMap.put(ActionType.DELETE, deleteAction);

        propertyGui.setActionMap(actionMap);
    }

}

