package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.swing.JTitledRoundedRectangle;
import org.perfcake.idea.model.Destination;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class DestinationComponent extends BasicDomElementComponent<Destination> {

    private JTitledRoundedRectangle destinationGui;

    public DestinationComponent(Destination domElement) {
        super(domElement);

        destinationGui = new JTitledRoundedRectangle(domElement.getClazz().getStringValue());
    }

    @Override
    public JComponent getComponent() {
        return destinationGui;
    }

    @Override
    public void reset() {
        super.reset();
        destinationGui.setTitle(myDomElement.getClazz().getStringValue());
    }
}
