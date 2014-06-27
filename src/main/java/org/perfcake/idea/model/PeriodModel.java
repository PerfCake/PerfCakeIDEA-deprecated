package org.perfcake.idea.model;

import org.perfcake.model.Scenario;

/**
 * Created by miron on 30.4.2014.
 */
public class PeriodModel extends AbstractScenarioModel {
    private static final String TYPE_PROPERTY = "type";
    private static final String VALUE_PROPERTY = "value";
    private static final String PERIOD_PROPERTY = "period";

    private Scenario.Reporting.Reporter.Destination.Period period;

    public PeriodModel(Scenario.Reporting.Reporter.Destination.Period period) {
        this.period = period;
    }

    /**
     *
     * @return PerfCake Period model intended for read only.
     */
    public Scenario.Reporting.Reporter.Destination.Period getPeriod() {
        return period;
    }

    public void setPeriod(Scenario.Reporting.Reporter.Destination.Period period) {
        Scenario.Reporting.Reporter.Destination.Period old = this.period;
        this.period = period;
        fireChangeEvent(PERIOD_PROPERTY, old, period);
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
