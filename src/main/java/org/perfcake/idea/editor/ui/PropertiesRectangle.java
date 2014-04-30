package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.PropertiesModel;
import org.perfcake.idea.editor.model.PropertyModel;
import org.perfcake.model.Property;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 29.4.2014.
 */
public class PropertiesRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private static final Logger LOG = Logger.getInstance(PropertiesRectangle.class);

    private static final String PROPERTIES_TITLE = "Scenario Properties";
    private PropertiesModel model;

    public PropertiesRectangle(PropertiesModel model) {
        super(PROPERTIES_TITLE);
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.model.getProperties() != null) {
            for (Property p : this.model.getProperties()) {
                PropertyModel propertyModel = new PropertyModel(p);
                PropertyRectangle propertyRectangle = new PropertyRectangle(propertyModel);
                propertyRectangle.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                add(propertyRectangle, BorderLayout.WEST);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertiesModel.PROPERTY_PROPERTY)) {
            Property oldValue = (Property) evt.getOldValue();
            Property newValue = (Property) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                PropertyRectangle propertyRectangle = new PropertyRectangle(new PropertyModel(newValue));
                propertyRectangle.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                add(propertyRectangle, BorderLayout.WEST);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = getComponents();
                    for (Component c : components) {
                        if (c instanceof PropertyRectangle) {
                            if (((PropertyRectangle) c).getModel().getProperty() == oldValue) {
                                remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("PropertyRectangle with property " + oldValue.toString() + " was not found in PropertiesRectangle");
                }
            }
        }
    }
}
