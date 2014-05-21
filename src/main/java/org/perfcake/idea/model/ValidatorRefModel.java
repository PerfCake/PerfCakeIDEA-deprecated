package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ValidatorRefModel extends AbstractScenarioModel {

    private static final String ID_PROPERTY = "id";
    private Scenario.Messages.Message.ValidatorRef model;

    public ValidatorRefModel(Scenario.Messages.Message.ValidatorRef model) {
        this.model = model;
    }

    public Scenario.Messages.Message.ValidatorRef getModel() {
        return model;
    }

    public void setId(String id) {
        String old = model.getId();
        model.setId(id);
        fireChangeEvent(ID_PROPERTY, old, id);
    }

}
