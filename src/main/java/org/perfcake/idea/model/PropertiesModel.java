package org.perfcake.idea.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class PropertiesModel extends AbstractScenarioModel {
    private static final Logger LOG = Logger.getInstance(PropertiesModel.class);

    public static final String PROPERTY_PROPERTY = "property";
    public static final String PROPERTIES_PROPERTY = "properties";

    private Scenario scenario;
    private Scenario.Properties properties;

    /**
     * Creates new PropertiesModel from scenario properties.
     * @param scenario which properties model should be created.
     */
    public PropertiesModel(@NotNull Scenario scenario) {
        this.scenario = scenario;
        this.properties = scenario.getProperties();
    }

    /**
     * @return PerfCake properties model intended for read only or null if no properties are present.
     */
    @Nullable
    public Scenario.Properties getProperties() {
        return this.properties;
    }

    /**
     * Sets new scenario properties from scenario
     * @param scenario which properties should be set
     */
    public void setProperties(@NotNull Scenario scenario) {
        Scenario.Properties old = this.properties;

        this. scenario = scenario;
        this.properties = scenario.getProperties();

        fireChangeEvent(PROPERTIES_PROPERTY, old, properties);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        ensurePropertiesExist();
        properties.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        ensurePropertiesExist();
        properties.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model. Property object should be in this model, otherwise error log is written.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = properties == null ? false : properties.getProperty().remove(property);
        if (!success) {
            LOG.error(getClass().getName() + ": Property " + property.getName() + " : " + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        deletePropertiesIfEmpty();
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }


    private void ensurePropertiesExist() {
        if (properties == null) {
            ObjectFactory factory = new ObjectFactory();
            properties = factory.createScenarioProperties();
            scenario.setProperties(properties);
        }
    }

    private void deletePropertiesIfEmpty() {
        if (properties.getProperty().isEmpty()) {
            properties = null;
            scenario.setProperties(null);
        }
    }
}
