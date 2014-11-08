package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidatorComponent extends BasicDomElementComponent<Validator> {

    private JPerfCakeIdeaRectangle validatorGui;

    public ValidatorComponent(Validator domElement) {
        super((Validator) domElement.createStableCopy());

        validatorGui = new JPerfCakeIdeaRectangle(getGuiText(), ColorType.VALIDATOR_FOREGROUND, ColorType.VALIDATOR_BACKGROUND);
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
        validatorGui.setTitle(getGuiText());
    }
}
