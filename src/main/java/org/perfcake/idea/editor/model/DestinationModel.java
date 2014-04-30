package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class DestinationModel extends AbstractScenarioModel {
    private static final String PROPERTY_PROPERTY = "property";
    private static final Logger LOG = Logger.getInstance(DestinationModel.class);
    private static final String PERIOD_PROPERTY = "period";
    private static final String CLAZZ_PROPERTY = "clazz";
    private static final String ENABLED_PROPERTY = "enabled";
    private Scenario.Reporting.Reporter.Destination destination;

    public DestinationModel(Scenario.Reporting.Reporter.Destination destination) {
        this.destination = destination;
    }

    public Scenario.Reporting.Reporter.Destination getDestination() {
        return destination;
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addPeriod(int index, @NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        destination.getPeriod().add(index, period);
        fireChangeEvent(PERIOD_PROPERTY, null, period);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addPeriod(@NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        destination.getPeriod().add(period);
        fireChangeEvent(PERIOD_PROPERTY, null, period);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deletePeriod(@NotNull Scenario.Reporting.Reporter.Destination.Period period) {
        boolean success = destination.getPeriod().remove(period);
        if (!success) {
            LOG.error("Period was not found in PerfCake JAXB model");
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
            LOG.error("Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
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
