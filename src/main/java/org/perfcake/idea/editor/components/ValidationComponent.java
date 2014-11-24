package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.ValidationDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidationComponent extends BasicDomElementComponent<Validation> {

    private static final String TITLE = "Validation";

    private JPerfCakeIdeaRectangle validationGui;

    public ValidationComponent(Validation domElement) {
        super((Validation) domElement.createStableCopy());

        validationGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.VALIDATION_FOREGROUND, ColorType.VALIDATION_BACKGROUND);
        validationGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Validation mockCopy = (Validation) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();

                        final ValidationDialog editDialog = new ValidationDialog(validationGui, mockCopy);
                        boolean ok = editDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Validation", mockCopy.getXmlElement().getContainingFile()) {
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


        addValidators();
    }


    @Override
    public JComponent getComponent() {
        return validationGui;
    }

    private void addValidators() {
        if (getDomElement().isValid()) {
            for (Validator v : myDomElement.getValidators()) {
                ValidatorComponent validatorComponent = new ValidatorComponent(v);
                addComponent(validatorComponent);
                validationGui.addComponent(validatorComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        validationGui.removeAllComponents();

        addValidators();
    }
}
