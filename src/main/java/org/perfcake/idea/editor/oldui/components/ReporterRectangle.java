package org.perfcake.idea.editor.oldui.components;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.oldmodel.DestinationModel;
import org.perfcake.idea.oldmodel.ReporterModel;
import org.perfcake.model.Scenario;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

        addDestinations();
    }

    private void addDestinations() {
        if (this.model.getReporter() != null) {
            for (Scenario.Reporting.Reporter.Destination d : this.model.getReporter().getDestination()) {
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
                                panel.remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error(getClass().getName() + ": DestinationRectangle with destination clazz " + oldValue.getClazz() + " was not found");
                }
            }
        }
        if (property.equals(ReporterModel.REPORTER_PROPERTY)) {
            updateRectangle();
        }
    }

    private void updateRectangle() {
        //update label
        label.setText((String) model.getReporter().getClazz());

        //remove old and add new destinations
        synchronized (getTreeLock()) {
            Component[] components = panel.getComponents();
            for (Component c : components) {
                if (c instanceof DestinationRectangle) {
                    panel.remove(c);
                }
            }
        }

        addDestinations();
    }
}
