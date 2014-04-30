package org.perfcake.idea.editor.model;

import org.perfcake.model.Property;

/**
 * Created by miron on 26.4.2014.
 */
public class PropertyModel extends AbstractScenarioModel {
    public static final String NAME_PROPERTY = "name";
    public static final String VALUE_PROPERTY = "value";

    private Property property;

    public PropertyModel(Property property) {
        this.property = property;
    }

    /*public PropertyModel(String name, String value){
        property = new ObjectFactory().createProperty();
        property.setName(name);
        property.setValue(value);
        fireChangeEvent(PropertiesModel, null, );

    }*/
    public Property getProperty() {
        return property;
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
        return property.getName() + ":" + property.getValue();
    }
}
