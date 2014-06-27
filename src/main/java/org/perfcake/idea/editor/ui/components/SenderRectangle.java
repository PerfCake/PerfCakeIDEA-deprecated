package org.perfcake.idea.editor.ui.components;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.PropertyModel;
import org.perfcake.idea.model.SenderModel;
import org.perfcake.model.Property;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class SenderRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private static final Logger LOG = Logger.getInstance(SenderRectangle.class);

    private SenderModel model;

    public SenderRectangle(SenderModel model) {
        super(model.getSender().getClazz());
        this.model = model;
        model.addPropertyChangeListener(this);

        addProperties();
    }

    private void addProperties() {
        for (Property p : this.model.getSender().getProperty()) {
            PropertyModel propertyModel = new PropertyModel(p);
            PropertyRectangle propertyRectangle = new PropertyRectangle(propertyModel);
            panel.add(propertyRectangle);
        }
    }

    //TODO: generalize with PropertiesRectangle
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SenderModel.CLAZZ_PROPERTY)) {
            label.setText((String) evt.getNewValue());
        }
        if (evt.getPropertyName().equals(SenderModel.PROPERTY_PROPERTY)) {
            Property oldValue = (Property) evt.getOldValue();
            Property newValue = (Property) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                PropertyRectangle propertyRectangle = new PropertyRectangle(new PropertyModel(newValue));
                panel.add(propertyRectangle);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = panel.getComponents();
                    for (Component c : components) {
                        if (c instanceof PropertyRectangle) {
                            if (((PropertyRectangle) c).getModel().getProperty() == oldValue) {
                                panel.remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("PropertyRectangle with property " + oldValue.toString() + " was not found in PropertiesRectangle");
                }
            }
        }
        if(evt.getPropertyName().equals(SenderModel.SENDER_PROPERTY)){
            updateRectangle();
        }


    }

    private void updateRectangle() {
        label.setText((String) model.getSender().getClazz());

        synchronized (getTreeLock()) {
            Component[] components = panel.getComponents();
            for (Component c : components) {
                if (c instanceof PropertyRectangle) {
                    panel.remove(c);
                }
            }
        }
        addProperties();
    }
}
