package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidationComponent extends BasicDomElementComponent<Validation> {

    private static final String TITLE = "Validation";

    private JPerfCakeIdeaRectangle validationGui;

    public ValidationComponent(Validation domElement) {
        super(domElement);

        validationGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.VALIDATION_FOREGROUND, ColorType.VALIDATION_BACKGROUND);

        addValidators();
    }


    @Override
    public JComponent getComponent() {
        return validationGui;
    }

    private void addValidators() {
        for (Validator v : myDomElement.getValidators()) {
            ValidatorComponent validatorComponent = new ValidatorComponent(v);
            addComponent(validatorComponent);
            validationGui.addComponent(validatorComponent.getComponent());
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
