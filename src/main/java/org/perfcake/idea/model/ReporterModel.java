package org.perfcake.idea.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ReporterModel extends AbstractScenarioModel {
    public static final String DESTINATION_PROPERTY = "destination";
    public static final String PROPERTY_PROPERTY = "property";
    public static final String CLAZZ_PROPERTY = "clazz";
    public static final String ENABLED_PROPERTY = "enabled";
    public static final String REPORTER_PROPERTY = "reporter";

    private static final Logger LOG = Logger.getInstance(ReporterModel.class);

    private Scenario.Reporting.Reporter reporter;

    public ReporterModel(Scenario.Reporting.Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     *
     * @return PerfCake Reporter model intended for read only.
     */
    public Scenario.Reporting.Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Scenario.Reporting.Reporter reporter) {
        Scenario.Reporting.Reporter old = this.reporter;
        this.reporter = reporter;
        fireChangeEvent(REPORTER_PROPERTY, old, reporter);
    }

    /**
     * Adds new destination at a specified position given by the index. Existent destinations in this position will be moved after this destination.
     *
     * @param index    at which should the destination be placed
     * @param destination to add
     */
    public void addDestination(int index, @NotNull Scenario.Reporting.Reporter.Destination destination) {
        reporter.getDestination().add(index, destination);
        fireChangeEvent(DESTINATION_PROPERTY, null, destination);
    }

    /**
     * Adds new destination to the end.
     *
     * @param destination to add
     */
    public void addDestination(@NotNull Scenario.Reporting.Reporter.Destination destination) {
        reporter.getDestination().add(destination);
        fireChangeEvent(DESTINATION_PROPERTY, null, destination);
    }

    /**
     * Deletes destination object from this model. Destination object should exist in this model.
     *
     * @param destination destination to delete
     */
    public void deleteDestination(@NotNull Scenario.Reporting.Reporter.Destination destination) {
        boolean success = reporter.getDestination().remove(destination);
        if (!success) {
            LOG.error(this.getClass().getName() + ": Destination was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(DESTINATION_PROPERTY, destination, null);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        reporter.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        reporter.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = reporter.getProperty().remove(property);
        if (!success) {
            LOG.error(this.getClass().getName() + ": Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }

    public void setClazz(String clazz) {
        String old = reporter.getClazz();
        reporter.setClazz(clazz);
        fireChangeEvent(CLAZZ_PROPERTY, old, clazz);
    }

    public void setEnabled(Boolean enabled) {
        boolean old = reporter.isEnabled();
        reporter.setEnabled(enabled);
        fireChangeEvent(ENABLED_PROPERTY, old, enabled);
    }
}
