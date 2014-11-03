package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Properties;
import org.perfcake.idea.model.Property;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class PropertiesComponent extends BasicDomElementComponent<Properties> {

    private static final String PROPERTIES_TITLE = "Scenario Properties";
    private JPerfCakeIdeaRectangle propertiesGui;

    public PropertiesComponent(Properties domElement) {
        super(domElement);

        propertiesGui = new JPerfCakeIdeaRectangle(PROPERTIES_TITLE, ColorType.PROPERTIES_FOREGROUND, ColorType.PROPERTIES_BACKGROUND);
        addProperties();
    }

    @Override
    public JComponent getComponent() {
        return propertiesGui;
    }

    private void addProperties() {
        for (Property p : myDomElement.getProperties()) {
            PropertyComponent propertyComponent = new PropertyComponent(p);
            addComponent(propertyComponent);
            propertiesGui.addComponent(propertyComponent.getComponent());
        }
    }

    @Override
    public void reset() {
        super.reset();

        getChildren().clear();
        propertiesGui.removeAllComponents();

        addProperties();

    }
}
