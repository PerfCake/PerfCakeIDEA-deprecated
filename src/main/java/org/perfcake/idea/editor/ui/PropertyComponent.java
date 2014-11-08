package org.perfcake.idea.editor.ui;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.PropertyEditDialog;
import org.perfcake.idea.editor.gui.JProperty;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Property;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponent extends BasicDomElementComponent<Property> {

    private JProperty propertyGui;
    private PropertyEditDialog editDialog;


    public PropertyComponent(final Property domElement) {
        super((Property) domElement.createStableCopy());
        propertyGui = new JProperty(domElement.getName().getStringValue(), domElement.getValue().getStringValue());

    }

    @Override
    public JComponent getComponent() {
        return propertyGui;
    }

    @Override
    public void commit() {
        super.commit();

        new WriteCommandAction(getDomElement().getModule().getProject(), "Set Property DOM Model" ,getDomElement().getXmlElement().getContainingFile()) {
            @Override
            protected void run(@NotNull Result result) throws Throwable {
                getDomElement().getName().setStringValue(propertyGui.getName());
                getDomElement().getValue().setStringValue(propertyGui.getValue());
            }
        }.execute();
    }

    @Override
    public void reset() {
        super.reset();
        if(getDomElement().isValid()){
            propertyGui.setName(getDomElement().getName().getStringValue());
            propertyGui.setValue(getDomElement().getValue().getStringValue());
        }else{

        }
    }

}

