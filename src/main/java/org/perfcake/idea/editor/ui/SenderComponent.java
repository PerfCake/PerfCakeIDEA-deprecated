package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.model.Sender;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class SenderComponent extends BasicDomElementComponent<Sender> {

    private JPerfCakeIdeaRectangle senderGui;

    public SenderComponent(Sender domElement) {
        super(domElement);

        senderGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.SENDER_FOREGROUND, ColorType.SENDER_BACKGROUND);
    }

    @Override
    public JComponent getComponent() {
        return senderGui;
    }

    @Override
    public void reset() {
        super.reset();

        senderGui.setTitle(getDomElement().getClazz().getStringValue());

        getChildren().clear();
        senderGui.removeAllComponents();

        addProperties();
    }

    private void addProperties() {
        for (Property p : myDomElement.getProperties()) {
            PropertyComponent propertyComponent = new PropertyComponent(p);
            addComponent(propertyComponent);
            senderGui.addComponent(propertyComponent.getComponent());
        }
    }
}
