package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ValidatorModel extends AbstractScenarioModel {
    public static final String ID_PROPERTY = "id";
    public static final String VALUE_PROPERTY = "value";
    public static final String CLAZZ_PROPERTY = "clazz";

    private Scenario.Validation.Validator model;

    public ValidatorModel(Scenario.Validation.Validator model) {
        this.model = model;
    }

    public Scenario.Validation.Validator getValidator() {
        return model;
    }

    public void setId(String id) {
        String old = model.getId();
        model.setId(id);
        fireChangeEvent(ID_PROPERTY, old, id);
    }

    public void setValue(String value) {
        String old = model.getValue();
        model.setValue(value);
        fireChangeEvent(VALUE_PROPERTY, old, value);
    }

    public void setClazz(String clazz) {
        String old = model.getClazz();
        model.setClazz(clazz);
        fireChangeEvent(CLAZZ_PROPERTY, old, clazz);
    }


}
