package org.perfcake.idea.editor.ui;

import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.model.ValidationModel;
import org.perfcake.idea.editor.model.ValidatorModel;
import org.perfcake.model.Scenario;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class ValidationRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private ValidationModel model;

    public ValidationRectangle(ValidationModel model) {
        super("Validation");
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.model.getValidation() != null && !this.model.getValidation().getValidator().isEmpty()) {
            for (Scenario.Validation.Validator v : this.model.getValidation().getValidator()) {
                ValidatorModel validatorModel = new ValidatorModel(v);
                ValidatorRectangle validatorRectangle = new ValidatorRectangle(validatorModel);
                panel.add(validatorRectangle);
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == ValidationModel.VALIDATOR_PROPERTY) {
            Scenario.Validation.Validator oldValue = (Scenario.Validation.Validator) evt.getOldValue();
            Scenario.Validation.Validator newValue = (Scenario.Validation.Validator) evt.getNewValue();

            if (oldValue == null && newValue != null) {
                ValidatorRectangle validatorRectangle = new ValidatorRectangle(new ValidatorModel(newValue));
                panel.add(validatorRectangle);
            }

            if (oldValue != null && newValue == null) {
                synchronized (getTreeLock()) {
                    Component[] components = panel.getComponents();
                    for (Component c : components) {
                        if (c instanceof ValidatorRectangle) {
                            if (((ValidatorRectangle) c).getModel().getValidator() == oldValue) {
                                remove(c);
                                return;
                            }
                        }
                    }
                    //LOG.error("PropertyRectangle with property " + oldValue.toString() + " was not found in PropertiesRectangle");
                }
            }
        }
    }
}
