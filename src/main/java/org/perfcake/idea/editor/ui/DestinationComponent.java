package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Destination;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class DestinationComponent extends BasicDomElementComponent<Destination> {

    private JPerfCakeIdeaRectangle destinationGui;

    public DestinationComponent(Destination domElement) {
        super((Destination) domElement.createStableCopy());

        destinationGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.DESTINATION_FOREGROUND, ColorType.DESTINATION_BACKGROUND);
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
