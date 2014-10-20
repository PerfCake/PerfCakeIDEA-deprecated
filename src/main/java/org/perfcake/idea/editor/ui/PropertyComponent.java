package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.reflect.AbstractDomChildrenDescription;
import com.intellij.util.xml.reflect.DomChildrenDescription;
import com.intellij.util.xml.reflect.DomGenericInfo;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import com.intellij.util.xml.ui.TextControl;
import com.intellij.util.xml.ui.TextPanel;
import com.sun.java.browser.plugin2.DOM;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private JPanel myPane = new JPanel();
    private TextPanel name = new TextPanel();
    private TextPanel value = new TextPanel();

    public PropertyComponent(Property domElement) {
        super(domElement);
        DomGenericInfo genericInfo = domElement.getGenericInfo();
        java.util.List<? extends AbstractDomChildrenDescription> childrenDescriptions = genericInfo.getChildrenDescriptions();
        for(AbstractDomChildrenDescription childrenDescription: childrenDescriptions){
            DomChildrenDescription d = (DomChildrenDescription) childrenDescription;
            System.out.println(d.getXmlElementName());
        }
        bindProperties(domElement);

        myPane.add(name);
        myPane.add(value);
    }

    @Override
    public JComponent getComponent() {
        return myPane;
    }
}
