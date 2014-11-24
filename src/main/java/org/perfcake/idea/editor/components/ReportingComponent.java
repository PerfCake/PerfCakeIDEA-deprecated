package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.ReportingDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class ReportingComponent extends BasicDomElementComponent<Reporting> {

    private static final String TITLE = "Reporting";

    private JPerfCakeIdeaRectangle reportingGui;

    public ReportingComponent(Reporting domElement) {
        super((Reporting) domElement.createStableCopy());

        reportingGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.REPORTING_FOREGROUND, ColorType.REPORTING_BACKGROUND);
        reportingGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Reporting mockCopy = (Reporting) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();
                        final ReportingDialog reportingDialog = new ReportingDialog(reportingGui, mockCopy);
                        boolean ok = reportingDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Reporting", mockCopy.getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(reportingDialog.getMockCopy());
                                }
                            }.execute();
                        }

                    }
                }
            }
        });


        addReporters();
    }


    @Override
    public JComponent getComponent() {
        return reportingGui;
    }

    private void addReporters() {
        if (getDomElement().isValid()) {
            for (Reporter r : myDomElement.getReporters()) {
                ReporterComponent reporterComponent = new ReporterComponent(r);
                addComponent(reporterComponent);
                reportingGui.addComponent(reporterComponent.getComponent());
            }
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
