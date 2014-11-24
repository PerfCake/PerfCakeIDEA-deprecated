package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.DestinationDialog;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Destination;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class DestinationComponent extends BasicDomElementComponent<Destination> {

    private JPerfCakeIdeaRectangle destinationGui;

    public DestinationComponent(Destination domElement) {
        super((Destination) domElement.createStableCopy());

        destinationGui = new JPerfCakeIdeaRectangle(domElement.getClazz().getStringValue(), ColorType.DESTINATION_FOREGROUND, ColorType.DESTINATION_BACKGROUND);
        destinationGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Destination mockCopy = (Destination) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();
                        final DestinationDialog destinationDialog = new DestinationDialog(destinationGui, mockCopy, Mode.EDIT);
                        boolean ok = destinationDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Destination", getDomElement().getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(destinationDialog.getMockCopy());
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
        return destinationGui;
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            destinationGui.setTitle(myDomElement.getClazz().getStringValue());
        }
    }
}
