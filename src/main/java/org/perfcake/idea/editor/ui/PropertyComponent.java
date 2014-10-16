package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import com.intellij.util.xml.ui.TextControl;
import com.intellij.util.xml.ui.TextPanel;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private JPanel myPane = new JPanel();
    private TextPanel name;
    private TextPanel value;

    public PropertyComponent(Property domElement) {
        super(domElement);
        bindProperties();

        myPane.add(name);
        myPane.add(value);
    }

    @Override
    public JComponent getComponent() {
        return myPane;
    }
}
