package org.perfcake.idea.editor.ui.components;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.ReporterModel;
import org.perfcake.idea.model.ReportingModel;
import org.perfcake.model.Scenario;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by miron on 11.5.2014.
 */
public class ReportingRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private static final String TITLE = "Reporting";
    private static final Logger LOG = Logger.getInstance(ReportingRectangle.class);

    private final ReportingModel model;

    public ReportingRectangle(ReportingModel model) {
        super(TITLE);
        this.model = model;
        model.addPropertyChangeListener(this);

        addReporters();
    }

    private void addReporters() {
        if(this.model.getReporting() != null){
            for (Scenario.Reporting.Reporter r : this.model.getReporting().getReporter()) {
                ReporterModel reporterModel = new ReporterModel(r);
                ReporterRectangle reporterRectangle = new ReporterRectangle(reporterModel);
                panel.add(reporterRectangle);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ReportingModel.REPORTER_PROPERTY)) {
            Scenario.Reporting.Reporter oldValue = (Scenario.Reporting.Reporter) evt.getOldValue();
            Scenario.Reporting.Reporter newValue = (Scenario.Reporting.Reporter) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                ReporterModel reporterModel = new ReporterModel(newValue);
                ReporterRectangle reporterRectangle = new ReporterRectangle(reporterModel);
                panel.add(reporterRectangle);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = panel.getComponents();
                    for (Component c : components) {
                        if (c instanceof ReporterRectangle) {
                            if (((ReporterRectangle) c).getModel().getReporter() == oldValue) {
                                panel.remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("ReporterRectangle with reporter clazz " + oldValue.getClazz() + " was not found in ReportingRectangle");
                }
            }
        }
        if(evt.getPropertyName().equals(ReportingModel.REPORTING_PROPERTY)){
            updateRectangle();
        }
    }

    private void updateRectangle() {
        synchronized (getTreeLock()) {
            Component[] components = panel.getComponents();
            for (Component c : components) {
                if (c instanceof ReporterRectangle) {
                    panel.remove(c);
                }
            }
        }
        addReporters();
    }
}
