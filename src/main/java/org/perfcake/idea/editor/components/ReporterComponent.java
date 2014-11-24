package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.ReporterDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class ReporterComponent extends BasicDomElementComponent<Reporter> {

    private JPerfCakeIdeaRectangle reporterGui;

    public ReporterComponent(Reporter domElement) {
        super((Reporter) domElement.createStableCopy());

        reporterGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.REPORTER_FOREGROUND, ColorType.REPORTER_BACKGROUND);
        reporterGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Reporter mockCopy = (Reporter) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();
                        final ReporterDialog reporterDialog = new ReporterDialog(reporterGui, mockCopy, Mode.EDIT);
                        boolean ok = reporterDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Reporter", getDomElement().getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(reporterDialog.getMockCopy());
                                }
                            }.execute();
                        }

                    }
                }
            }
        });


        addDestinations();
    }


    @Override
    public JComponent getComponent() {
        return reporterGui;
    }


    private void addDestinations() {

            for (Destination d : getDomElement().getDestinations()) {
                DestinationComponent destinationComponent = new DestinationComponent(d);
                addComponent(destinationComponent);
                reporterGui.addComponent(destinationComponent.getComponent());
            }

    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            reporterGui.setTitle(getDomElement().getClazz().getStringValue());
            getChildren().clear();
            reporterGui.removeAllComponents();
            addDestinations();
        }
    }
}
