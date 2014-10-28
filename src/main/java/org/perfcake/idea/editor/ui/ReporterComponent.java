package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.swing.JTitledRoundedRectangle;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ReporterComponent extends BasicDomElementComponent<Reporter> {

    private JTitledRoundedRectangle reporterGui;

    public ReporterComponent(Reporter domElement) {
        super(domElement);

        reporterGui = new JTitledRoundedRectangle(domElement.getClazz().getStringValue());

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

        reporterGui.setTitle(getDomElement().getClazz().getStringValue());

        getChildren().clear();
        reporterGui.removeAllComponents();

        addDestinations();
    }
}
