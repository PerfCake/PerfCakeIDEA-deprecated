package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.Property;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private JTitledRoundedRectangle propertyGui;


    public PropertyComponent(Property domElement) {
        super(domElement);
        propertyGui = new JTitledRoundedRectangle(domElement.getName().getStringValue() + ":" + domElement.getValue().getStringValue());

    }

    @Override
    public JComponent getComponent() {
        return propertyGui;
    }


    @Override
    public void reset() {
        super.reset();
        propertyGui.setTitle(myDomElement.getName().getStringValue() + ":" + myDomElement.getValue().getStringValue());
    }
}

