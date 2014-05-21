package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.DestinationModel;
import org.perfcake.idea.editor.model.ReporterModel;
import org.perfcake.model.Scenario;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by miron on 11.5.2014.
 */
public class ReporterRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private static final Logger LOG = Logger.getInstance(ReporterRectangle.class);
    private ReporterModel model;


    public ReporterRectangle(ReporterModel model) {
        super(model.getReporter().getClazz());
        this.model = model;

        model.addPropertyChangeListener(this);
        List<Scenario.Reporting.Reporter.Destination> destinations = this.model.getReporter().getDestination();
        if (!destinations.isEmpty()) {
            for (Scenario.Reporting.Reporter.Destination d : destinations) {
                DestinationModel destinationModel = new DestinationModel(d);
                DestinationRectangle destinationRectangle = new DestinationRectangle(destinationModel);
                panel.add(destinationRectangle);
            }
        }

    }

    public ReporterModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();
        if (property.equals(ReporterModel.CLAZZ_PROPERTY)) {
            label.setText((String) evt.getNewValue());
        }

        if (property.equals(ReporterModel.ENABLED_PROPERTY)) {
            //TODO
        }
        if (property.equals(ReporterModel.DESTINATION_PROPERTY)) {
            Scenario.Reporting.Reporter.Destination oldValue = (Scenario.Reporting.Reporter.Destination) evt.getOldValue();
            Scenario.Reporting.Reporter.Destination newValue = (Scenario.Reporting.Reporter.Destination) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                DestinationModel d = new DestinationModel(newValue);
                DestinationRectangle destinationRectangle = new DestinationRectangle(d);
                panel.add(destinationRectangle);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = panel.getComponents();
                    for (Component c : components) {
                        if (c instanceof DestinationRectangle) {
                            if (((DestinationRectangle) c).getModel().getDestination() == oldValue) {
                                remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("DestinationRectangle with destination clazz " + oldValue.getClazz() + " was not found in ReporterRectangle");
                }
            }
        }
    }
}
