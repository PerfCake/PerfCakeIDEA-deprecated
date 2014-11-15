package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.PropertyEditMouseListener;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.editor.gui.PropertyGui;
import org.perfcake.idea.model.Property;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private PropertyGui propertyGui;
    private PropertyDialog editDialog;


    public PropertyComponent(final Property domElement) {
        super((Property) domElement.createStableCopy());
        propertyGui = new PropertyGui(domElement.getName().getStringValue(), domElement.getValue().getStringValue());
        propertyGui.addMouseListener(new PropertyEditMouseListener(propertyGui, getDomElement()));
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

}

