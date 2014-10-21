package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.Validator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidatorComponent extends BasicDomElementComponent<Validator> {

    private JTitledRoundedRectangle validatorGui;

    public ValidatorComponent(Validator domElement) {
        super(domElement);

        validatorGui = new JTitledRoundedRectangle(getGuiText());
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
