package org.perfcake.idea.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class SenderModel extends AbstractScenarioModel {
    private static final Logger LOG = Logger.getInstance(SenderModel.class);

    public static final String CLAZZ_PROPERTY = "clazz";
    public static final String PROPERTY_PROPERTY = "property";
    public static final String SENDER_PROPERTY = "sender";

    private Scenario.Sender sender;

    public SenderModel(@NotNull Scenario.Sender sender) {
        this.sender = sender;
    }

    /**
     *
     * @return PerfCake sender model intended for read only.
     */
    @NotNull
    public Scenario.Sender getSender() {
        return sender;
    }

    public void setSender(@NotNull Scenario.Sender sender) {
        Scenario.Sender old = this.sender;
        this.sender = sender;
        fireChangeEvent(SENDER_PROPERTY, old, sender);
    }

    public void setClazz(String clazz) {
        String old = sender.getClazz();
        sender.setClazz(clazz);
        fireChangeEvent(CLAZZ_PROPERTY, old, clazz);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        sender.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        sender.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model. Property object should exist in this model, otherwise error is logged.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = sender.getProperty().remove(property);
        if (!success) {
            LOG.error(this.getClass().getName() + ": Property " + property.getName() + " : " + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }
}
