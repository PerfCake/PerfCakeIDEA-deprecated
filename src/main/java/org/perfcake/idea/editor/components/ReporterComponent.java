package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ReporterComponent extends BasicDomElementComponent<Reporter> {

    private JPerfCakeIdeaRectangle reporterGui;

    public ReporterComponent(Reporter domElement) {
        super((Reporter) domElement.createStableCopy());

        reporterGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.REPORTER_FOREGROUND, ColorType.REPORTER_BACKGROUND);

        addDestinations();
    }


    @Override
    public JComponent getComponent() {
        return reporterGui;
    }


    private void addDestinations() {
        if (getDomElement().isValid()) {
            for (Destination d : getDomElement().getDestinations()) {
                DestinationComponent destinationComponent = new DestinationComponent(d);
                addComponent(destinationComponent);
                reporterGui.addComponent(destinationComponent.getComponent());
            }
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
