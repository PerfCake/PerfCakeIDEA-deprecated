package org.perfcake.idea.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class DestinationModel extends AbstractScenarioModel {
    public static final String PROPERTY_PROPERTY = "property";
    public static final String PERIOD_PROPERTY = "period";
    public static final String CLAZZ_PROPERTY = "clazz";
    public static final String ENABLED_PROPERTY = "enabled";
    public static final String DESTINATION_PROPERTY = "destination";

    private static final Logger LOG = Logger.getInstance(DestinationModel.class);

    private Scenario.Reporting.Reporter.Destination destination;

    public DestinationModel(Scenario.Reporting.Reporter.Destination destination) {
        this.destination = destination;
    }

    /**
     *
     * @return PerfCake Destination model intended for read only
     */
    public Scenario.Reporting.Reporter.Destination getDestination() {
        return destination;
    }

    public void setDestination(Scenario.Reporting.Reporter.Destination destination) {
        Scenario.Reporting.Reporter.Destination old = this.destination;
        this.destination = destination;
        fireChangeEvent(DESTINATION_PROPERTY, old, destination);
    }

    /**
     * Adds new period at a specified position given by the index. Existent periods in this position will be moved after this period.
     *
     * @param index    at which should the period be placed
     * @param period to add
     */
    public void addPeriod(int index, @NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        destination.getPeriod().add(index, period);
        fireChangeEvent(PERIOD_PROPERTY, null, period);
    }

    /**
     * Adds new period to the end.
     *
     * @param period to add
     */
    public void addPeriod(@NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        destination.getPeriod().add(period);
        fireChangeEvent(PERIOD_PROPERTY, null, period);
    }

    /**
     * Deletes period object from this model. Period object should exist in this model.
     *
     * @param period to delete
     */
    public void deletePeriod(@NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        boolean success = destination.getPeriod().remove(period);
        if (!success) {
            LOG.error(getClass().getName() + ": Period was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PERIOD_PROPERTY, period, null);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        destination.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        destination.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = destination.getProperty().remove(property);
        if (!success) {
            LOG.error(getClass().getName() + ": Property " + property.getName() + " : " + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }

    public void setClazz(String clazz) {
        String old = destination.getClazz();
        destination.setClazz(clazz);
        fireChangeEvent(CLAZZ_PROPERTY, old, clazz);
    }

    public void setEnabled(Boolean enabled) {
        boolean old = destination.isEnabled();
        destination.setEnabled(enabled);
        fireChangeEvent(ENABLED_PROPERTY, old, enabled);
    }


}
