package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Property;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private JPerfCakeIdeaRectangle propertyGui;


    public PropertyComponent(Property domElement) {
        super(domElement);
        propertyGui = new JPerfCakeIdeaRectangle(getTitle(), ColorType.PROPERTY_FOREGROUND, ColorType.PROPERTY_BACKGROUND);

    }

    @Override
    public JComponent getComponent() {
        return propertyGui;
    }


    @Override
    public void reset() {
        super.reset();
        propertyGui.setTitle(getTitle());
    }

    private String getTitle() {
        return myDomElement.getName().getStringValue() + ":" + myDomElement.getValue().getStringValue();
    }
}

