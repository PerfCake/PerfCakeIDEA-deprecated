package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

/**
 * Created by miron on 30.4.2014.
 */
public class PeriodModel extends AbstractScenarioModel {
    private static final String TYPE_PROPERTY = "type";
    private static final String VALUE_PROPERTY = "value";
    private Scenario.Reporting.Reporter.Destination.Period period;

    public PeriodModel(Scenario.Reporting.Reporter.Destination.Period period) {

        this.period = period;
    }

    public Scenario.Reporting.Reporter.Destination.Period getPeriod() {
        return period;
    }

    public void setType(String type) {
        String old = period.getType();
        period.setType(type);
        fireChangeEvent(TYPE_PROPERTY, old, type);
    }

    public void setValue(String value) {
        String old = period.getValue();
        period.setValue(value);
        fireChangeEvent(VALUE_PROPERTY, old, value);
    }
}
