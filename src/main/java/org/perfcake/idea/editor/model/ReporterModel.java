package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class ReporterModel extends AbstractScenarioModel {
    private static final String DESTINATION_PROPERTY = "destination";
    private static final Logger LOG = Logger.getInstance(ReporterModel.class);
    private static final String PROPERTY_PROPERTY = "property";
    private static final String CLAZZ_PROPERTY = "clazz";
    private static final String ENABLED_PROPERTY = "enabled";
    private Scenario.Reporting.Reporter reporter;

    public ReporterModel(Scenario.Reporting.Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addDestination(int index, @NotNull Scenario.Reporting.Reporter.Destination destination) {
        reporter.getDestination().add(index, destination);
        fireChangeEvent(DESTINATION_PROPERTY, null, destination);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addDestination(@NotNull Scenario.Reporting.Reporter.Destination destination) {
        reporter.getDestination().add(destination);
        fireChangeEvent(DESTINATION_PROPERTY, null, destination);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteDestination(@NotNull Scenario.Reporting.Reporter.Destination destination) {
        boolean success = reporter.getDestination().remove(destination);
        if (!success) {
            LOG.error("Destination was not found in PerfCake JAXB model");
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
            LOG.error("Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
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
