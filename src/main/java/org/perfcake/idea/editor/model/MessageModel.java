package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class MessageModel extends AbstractScenarioModel {
    public static final String HEADER_PROPERTY = "header";
    public static final String PROPERTY_PROPERTY = "property";
    public static final String VALIDATOR_REF_PROPERTY = "validatorRef";
    public static final String URI_PROPERTY = "uri";
    public static final String MULTIPLICITY_PROPERTY = "multiplicity";

    private static final Logger LOG = Logger.getInstance(MessageModel.class);

    private Scenario.Messages.Message message;

    public MessageModel(Scenario.Messages.Message message) {
        this.message = message;
    }

    public Scenario.Messages.Message getMessage() {
        return message;
    }

    /**
     * Adds new header at a specified position given by the index. Existent headers in this position will be moved after this header.
     *
     * @param index  at which should the header be placed
     * @param header to add
     */
    public void addHeader(int index, @NotNull Header header) {
        message.getHeader().add(index, header);
        fireChangeEvent(HEADER_PROPERTY, null, header);
    }

    /**
     * Adds new header to the end.
     *
     * @param header to add
     */
    public void addHeader(@NotNull Header header) {
        message.getHeader().add(header);
        fireChangeEvent(HEADER_PROPERTY, null, header);
    }

    /**
     * Deletes header object from this model.
     *
     * @param header header to delete
     */
    public void deleteHeader(@NotNull Header header) {
        boolean success = message.getHeader().remove(header);
        if (!success) {
            LOG.error("Header " + header.getName() + " : " + header.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(HEADER_PROPERTY, header, null);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        message.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        message.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = message.getProperty().remove(property);
        if (!success) {
            LOG.error("Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }

    /**
     * Adds new validatorRef at a specified position given by the index. Existent ValidatorRefs in this position will be moved after this ValidatorRef.
     *
     * @param index        at which should the ValidatorRef be placed
     * @param validatorRef to add
     */
    public void addValidatorRef(int index, @NotNull Scenario.Messages.Message.ValidatorRef validatorRef) {
        message.getValidatorRef().add(index, validatorRef);
        fireChangeEvent(VALIDATOR_REF_PROPERTY, null, validatorRef);
    }

    /**
     * Adds new validatorRef to the end.
     *
     * @param validatorRef to add
     */
    public void addProperty(@NotNull Scenario.Messages.Message.ValidatorRef validatorRef) {
        message.getValidatorRef().add(validatorRef);
        fireChangeEvent(VALIDATOR_REF_PROPERTY, null, validatorRef);
    }

    /**
     * Deletes validatorRef object from this model.
     *
     * @param validatorRef property to delete
     */
    public void deleteValidatorRef(@NotNull Scenario.Messages.Message.ValidatorRef validatorRef) {
        boolean success = message.getValidatorRef().remove(validatorRef);
        if (!success) {
            LOG.error("ValidatorRef " + validatorRef.getId() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(VALIDATOR_REF_PROPERTY, validatorRef, null);
    }

    public void setUri(String uri) {
        String old = message.getUri();
        message.setUri(uri);
        fireChangeEvent(URI_PROPERTY, old, uri);
    }

    public void setMultiplicity(String multiplicity) {
        String old = message.getMultiplicity();
        message.setMultiplicity(multiplicity);
        fireChangeEvent(MULTIPLICITY_PROPERTY, old, multiplicity);
    }
}
