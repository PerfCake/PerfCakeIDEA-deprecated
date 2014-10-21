package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ReportingComponent extends BasicDomElementComponent<Reporting> {

    private static final String TITLE = "Reporting";

    private JTitledRoundedRectangle reportingGui;

    public ReportingComponent(Reporting domElement) {
        super(domElement);

        reportingGui = new JTitledRoundedRectangle(TITLE);

        addReporters();
    }


    @Override
    public JComponent getComponent() {
        return reportingGui;
    }

    private void addReporters() {
        for (Reporter r : myDomElement.getReporters()) {
            ReporterComponent reporterComponent = new ReporterComponent(r);
            addComponent(reporterComponent);
            reportingGui.addComponent(reporterComponent.getComponent());
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        reportingGui.removeAllComponents();

        addReporters();
    }
}
