package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ReportingComponent extends BasicDomElementComponent<Reporting> {

    private static final String TITLE = "Reporting";

    private JPerfCakeIdeaRectangle reportingGui;

    public ReportingComponent(Reporting domElement) {
        super(domElement);

        reportingGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.REPORTING_FOREGROUND, ColorType.REPORTING_BACKGROUND);

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
