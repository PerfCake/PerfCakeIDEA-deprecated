package org.perfcake.idea.model;

import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;

/**
 * Created by miron on 26.4.2014.
 */
public class PropertyModel extends AbstractScenarioModel {
    public static final String NAME_PROPERTY = "name";
    public static final String VALUE_PROPERTY = "value";
    public static final String PROPERTY_PROPERTY = "property";

    private Property property;

    public PropertyModel(@NotNull Property property) {
        this.property = property;
    }

    /**
     *
     * @return PerfCake property model intended for read only.
     */
    @NotNull
    public Property getProperty() {
        return property;
    }

    public void setProperty(@NotNull Property property) {
        Property old = this.property;
        this.property = property;
        fireChangeEvent(PROPERTY_PROPERTY, old, property);
    }

    public void setName(String name) {
        String old = property.getName();
        property.setName(name);
        fireChangeEvent(NAME_PROPERTY, old, name);
    }

    public void setValue(String value) {
        String old = property.getValue();
        property.setValue(value);
        fireChangeEvent(VALUE_PROPERTY, old, value);
    }

    @Override
    public String toString() {
        return property.getName() + " : " + property.getValue();
    }
}
