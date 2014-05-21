package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.ReporterModel;
import org.perfcake.idea.editor.model.ReportingModel;
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
        List<Scenario.Reporting.Reporter> reporters = this.model.getReporting() == null ? null : this.model.getReporting().getReporter();
        if (reporters != null && !reporters.isEmpty()) {
            for (Scenario.Reporting.Reporter r : reporters) {
                addReporter(r);
            }
        }
    }

    private void addReporter(Scenario.Reporting.Reporter r) {
        ReporterModel reporterModel = new ReporterModel(r);
        ReporterRectangle reporterRectangle = new ReporterRectangle(reporterModel);
        panel.add(reporterRectangle);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ReportingModel.REPORTER_PROPERTY)) {
            Scenario.Reporting.Reporter oldValue = (Scenario.Reporting.Reporter) evt.getOldValue();
            Scenario.Reporting.Reporter newValue = (Scenario.Reporting.Reporter) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                addReporter(newValue);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = panel.getComponents();
                    for (Component c : components) {
                        if (c instanceof ReporterRectangle) {
                            if (((ReporterRectangle) c).getModel().getReporter() == oldValue) {
                                remove(c);
                                return;
                            }
                        }
                    }
                    LOG.error("ReporterRectangle with reporter clazz " + oldValue.getClazz() + " was not found in ReportingRectangle");
                }
            }
        }
    }
}
