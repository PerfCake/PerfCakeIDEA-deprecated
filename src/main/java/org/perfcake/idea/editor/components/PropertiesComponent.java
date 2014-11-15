package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.PropertiesDialog;
import org.perfcake.idea.editor.gui.PropertiesGui;
import org.perfcake.idea.model.Properties;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class PropertiesComponent extends BasicDomElementComponent<Properties> {


    private PropertiesGui propertiesGui;

    public PropertiesComponent(Properties domElement) {
        super((Properties) domElement.createStableCopy());


        propertiesGui = new PropertiesGui();//domElement.getMockCopy());
        propertiesGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Properties mockCopy = (Properties) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();

                        final PropertiesDialog editDialog = new PropertiesDialog(propertiesGui, (Properties) mockCopy);
                        boolean ok = editDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Properties", mockCopy.getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(mockCopy);
                                    reset();
                                }
                            }.execute();
                        }

                    }
                }
            }
        });

        addProperties();
    }

    @Override
    public JComponent getComponent() {
        return propertiesGui;
    }

    private void addProperties() {
        if (getDomElement().isValid()) {
            for (Property p : myDomElement.getProperties()) {
                PropertyComponent propertyComponent = new PropertyComponent(p);
                addComponent(propertyComponent);
                propertiesGui.addComponent(propertyComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();

        getChildren().clear();
        propertiesGui.removeAllComponents();

        addProperties();

    }
}
