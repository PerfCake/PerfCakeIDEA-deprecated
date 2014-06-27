package org.perfcake.idea.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ValidationModel extends AbstractScenarioModel {
    public static final String VALIDATOR_PROPERTY = "validator";
    public static final String VALIDATION_PROPERTY = "validation";

    private static final Logger LOG = Logger.getInstance(ValidationModel.class);
    private Scenario.Validation validation;

    public ValidationModel(Scenario.Validation validation) {
        this.validation = validation;
    }

    /**
     *
     * @return PerfCake Validation model intended for read only.
     */
    public Scenario.Validation getValidation() {
        return validation;
    }

    public void setValidation(Scenario.Validation validation) {
        Scenario.Validation old = this.validation;
        this.validation = validation;
        fireChangeEvent(VALIDATION_PROPERTY, old, validation);
    }

    /**
     * Adds new validator at a specified position given by the index. Existent validator in this position will be moved after this validator.
     *
     * @param index     at which should the property be placed
     * @param validator to add
     */
    public void addValidator(int index, @NotNull Scenario.Validation.Validator validator) {
        validation.getValidator().add(index, validator);
        fireChangeEvent(VALIDATOR_PROPERTY, null, validator);
    }

    /**
     * Adds new validator to the end.
     *
     * @param validator to add
     */
    public void addValidator(@NotNull Scenario.Validation.Validator validator) {
        validation.getValidator().add(validator);
        fireChangeEvent(VALIDATOR_PROPERTY, null, validator);
    }

    /**
     * Deletes validator object from this model. Validator object should exist in this model.
     *
     * @param validator property to delete
     */
    public void deleteValidator(@NotNull Scenario.Validation.Validator validator) {
        boolean success = validation.getValidator().remove(validator);
        if (!success) {
            LOG.error(getClass().getName() + ": Validator " + validator.getClazz() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(VALIDATOR_PROPERTY, validator, null);
    }
}
