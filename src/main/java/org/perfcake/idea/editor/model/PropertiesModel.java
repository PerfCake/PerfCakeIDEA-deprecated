package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

import java.util.List;

/**
 * Created by miron on 22.4.2014.
 */
public class PropertiesModel extends AbstractScenarioModel {
    private static final Logger LOG = Logger.getInstance(PropertiesModel.class);

    public static final String PROPERTY_PROPERTY = "property";
    private Scenario scenario;
    private Scenario.Properties properties;

    public PropertiesModel(Scenario scenario) {
        this.scenario = scenario;
        this.properties = scenario.getProperties();
    }

    /**
     * @return List of PropertyModel
     */
    @Nullable
    public List<Property> getProperties() {
        //TODO not to modify
        if (properties != null) {
            return properties.getProperty();
        }
        return null;
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
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = properties == null ? false : properties.getProperty().remove(property);
        if (!success) {
            LOG.error("Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
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
