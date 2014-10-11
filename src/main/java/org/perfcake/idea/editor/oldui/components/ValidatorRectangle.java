package org.perfcake.idea.editor.oldui.components;

import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.oldmodel.ValidatorModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class ValidatorRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    ValidatorModel model;

    public ValidatorRectangle(ValidatorModel model) {
        super("(" + model.getValidator().getId() + ") " + model.getValidator().getClazz());
        this.model = model;
        model.addPropertyChangeListener(this);

    }

    public ValidatorModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ValidatorModel.ID_PROPERTY) ||
                evt.getPropertyName() == ValidatorModel.CLAZZ_PROPERTY ||
                evt.getPropertyName().equals(ValidatorModel.VALIDATOR_PROPERTY)) {
            label.setText("(" + model.getValidator().getId() + ") " + model.getValidator().getClazz());
        }
    }
}
