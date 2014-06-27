package org.perfcake.idea.model;

import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class RunModel extends AbstractScenarioModel {

    public static final String TYPE_PROPERTY = "type";
    public static final String VALUE_PROPERTY = "value";
    public static final String RUN_PROPERTY = "run";

    private Scenario.Generator.Run run;

    public RunModel(@NotNull Scenario.Generator.Run run) {
        this.run = run;
    }

    /**
     *
     * @return PerfCake run model intended for read only.
     */
    @NotNull
    public Scenario.Generator.Run getRun() {
        return run;
    }

    public void setRun(@NotNull Scenario.Generator.Run run) {
        Scenario.Generator.Run old = this.run;
        this.run = run;
        fireChangeEvent(RUN_PROPERTY, old, run);
    }

    public void setType(String type) {
        String old = run.getType();
        run.setType(type);
        fireChangeEvent(TYPE_PROPERTY, old, type);
    }

    public void setValue(String value) {
        String old = run.getValue();
        run.setValue(value);
        fireChangeEvent(VALUE_PROPERTY, old, value);
    }

    public String toString() {
        return run.getType() + " : " + run.getValue();
    }

}
