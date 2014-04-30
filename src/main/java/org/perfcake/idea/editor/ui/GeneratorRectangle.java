package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.GeneratorModel;
import org.perfcake.idea.editor.model.PropertyModel;
import org.perfcake.idea.editor.model.RunModel;
import org.perfcake.model.Property;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class GeneratorRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {

    private static final Logger LOG = Logger.getInstance(GeneratorRectangle.class);
    private GeneratorModel model;
    RunModel runModel;
    JLabel runLabel;

    public GeneratorRectangle(GeneratorModel model) {
        super(model.getGenerator().getClazz() + " (" + model.getGenerator().getThreads() + ")");
        this.model = model;
        model.addPropertyChangeListener(this);

        runModel = new RunModel(model.getGenerator().getRun());
        runModel.addPropertyChangeListener(this);
        runLabel = new JLabel(runModel.toString());
        add(runLabel);

        for (Property p : model.getGenerator().getProperty()) {
            PropertyModel propertyModel = new PropertyModel(p);
            PropertyRectangle propertyRectangle = new PropertyRectangle(propertyModel);
            propertyRectangle.setPreferredSize(new Dimension(1, 1));
            add(propertyRectangle);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();
        if (property == RunModel.TYPE_PROPERTY || property == RunModel.VALUE_PROPERTY) {
            runLabel.setText(evt.getSource().toString());
        }

        if (property == GeneratorModel.RUN_PROPERTY) {
            runModel.removePropertyChangeListener(this);
            runModel = (RunModel) evt.getNewValue();
            runModel.addPropertyChangeListener(this);
        }
        if (property == GeneratorModel.CLAZZ_PROPERTY || property == GeneratorModel.THREADS_PROPERTY) {
            label.setText(model.getGenerator().getClazz() + " (" + model.getGenerator().getThreads() + ")");
        }
        if (property == GeneratorModel.PROPERTY_PROPERTY) {
            Property oldValue = (Property) evt.getOldValue();
            Property newValue = (Property) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                PropertyRectangle propertyRectangle = new PropertyRectangle(new PropertyModel(newValue));
                add(propertyRectangle);
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
