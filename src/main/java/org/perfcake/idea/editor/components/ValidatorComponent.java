package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.ValidatorDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidatorComponent extends BasicDomElementComponent<Validator> {

    private JPerfCakeIdeaRectangle validatorGui;

    public ValidatorComponent(Validator domElement) {
        super((Validator) domElement.createStableCopy());

        validatorGui = new JPerfCakeIdeaRectangle(getGuiText(), ColorType.VALIDATOR_FOREGROUND, ColorType.VALIDATOR_BACKGROUND);
        validatorGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Validator mockCopy = (Validator) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();

                        final ValidatorDialog editDialog = new ValidatorDialog(validatorGui, mockCopy);
                        boolean ok = editDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Validator", mockCopy.getXmlElement().getContainingFile()) {
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
    }

    @Override
    public JComponent getComponent() {
        return validatorGui;
    }

    private String getGuiText() {
        return "(" + getDomElement().getId().getStringValue() + ") " + getDomElement().getClazz().getStringValue();
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            validatorGui.setTitle(getGuiText());
        }
    }
}
