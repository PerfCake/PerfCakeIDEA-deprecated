package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 22.4.2014.
 */
public class ScenarioComponentFactory {
    private final Scenario model;
    private final Map<ScenarioComponentType, AbstractScenarioModel> componentModelMap = new HashMap<>();

    public ScenarioComponentFactory(final Scenario model) {
        this.model = model;
    }

    public AbstractScenarioModel buildScenarioComponent(Scenario model, ScenarioComponentType componentType) {
        AbstractScenarioModel componentModel = null;
        switch (componentType) {
            case SCENARIO:
                break;
            case GENERATOR:
                break;
            case RUN:
                break;
            case MESSAGES:
                break;
            case MESSAGE:
                break;
            case VALIDATOR_REF:
                break;
            case PROPERTIES:
                break;
            case REPORTING:
                break;
            case REPORTER:
                break;
            case DESTINATION:
                break;
            case PERIOD:
                break;
            case SENDER:
                //componentModel = new SenderModel(model);
                break;
            case VALIDATION:
                break;
            case VALIDATOR:
                break;
        }
        return componentModel;
    }
}
