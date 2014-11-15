package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.SenderDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.model.Sender;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class SenderComponent extends BasicDomElementComponent<Sender> {

    private JPerfCakeIdeaRectangle senderGui;

    public SenderComponent(Sender domElement) {
        super((Sender) domElement.createStableCopy());

        senderGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.SENDER_FOREGROUND, ColorType.SENDER_BACKGROUND);
        senderGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Sender mockCopy = (Sender) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();
                        final SenderDialog senderDialog = new SenderDialog(senderGui, mockCopy);
                        boolean ok = senderDialog.showAndGet();
                        if (ok) {
                            new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Sender", getDomElement().getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(senderDialog.getMockCopy());
                                    //reset();
                                }
                            }.execute();
                        }
                    }
                }
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return senderGui;
    }

    @Override
    public void reset() {
        super.reset();

        getChildren().clear();
        senderGui.removeAllComponents();

        if (getDomElement().isValid()) {
            senderGui.setTitle(getDomElement().getClazz().getStringValue());
            addProperties();
        } else {
            senderGui.setTitle("INVALID");
        }
    }

    private void addProperties() {
        if(getDomElement().isValid()){
            for (Property p : myDomElement.getProperties()) {
                PropertyComponent propertyComponent = new PropertyComponent(p);
                addComponent(propertyComponent);
                senderGui.addComponent(propertyComponent.getComponent());
            }
        }
    }
}
