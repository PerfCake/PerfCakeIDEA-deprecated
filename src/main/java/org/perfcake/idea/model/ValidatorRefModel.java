package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ValidatorRefModel extends AbstractScenarioModel {

    private static final String ID_PROPERTY = "id";
    private static final String VALIDATOR_REF_PROPERTY = "validatorRef";
    private Scenario.Messages.Message.ValidatorRef model;

    public ValidatorRefModel(Scenario.Messages.Message.ValidatorRef model) {
        this.model = model;
    }

    /**
     *
     * @return PerfCake ValidatorRef model intended for read only.
     */
    public Scenario.Messages.Message.ValidatorRef getModel() {
        return model;
    }

    public void setModel(Scenario.Messages.Message.ValidatorRef model) {
        Scenario.Messages.Message.ValidatorRef old = model;
        this.model = model;
        fireChangeEvent(VALIDATOR_REF_PROPERTY, old, model);
    }

    public void setId(String id) {
        String old = model.getId();
        model.setId(id);
        fireChangeEvent(ID_PROPERTY, old, id);
    }

}
