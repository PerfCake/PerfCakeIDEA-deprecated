package org.perfcake.idea.editor.model;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 22.4.2014.
 */
public class GeneratorModel extends AbstractScenarioModel {

    public static final String RUN_PROPERTY = "run";
    public static final String PROPERTY_PROPERTY = "property";
    public static final String CLAZZ_PROPERTY = "clazz";
    public static final String THREADS_PROPERTY = "clazz";

    private static final Logger LOG = Logger.getInstance(GeneratorModel.class);

    private Scenario.Generator generator;

    public GeneratorModel(Scenario.Generator generator) {
        this.generator = generator;
    }

    public Scenario.Generator getGenerator() {
        return generator;
    }

    public void setRun(Scenario.Generator.Run run) {
        Scenario.Generator.Run old = generator.getRun();
        generator.setRun(run);
        fireChangeEvent(RUN_PROPERTY, old, run);
    }

    /**
     * Adds new property at a specified position given by the index. Existent properties in this position will be moved after this property.
     *
     * @param index    at which should the property be placed
     * @param property to add
     */
    public void addProperty(int index, @NotNull Property property) {
        generator.getProperty().add(index, property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Adds new property to the end.
     *
     * @param property to add
     */
    public void addProperty(@NotNull Property property) {
        generator.getProperty().add(property);
        fireChangeEvent(PROPERTY_PROPERTY, null, property);
    }

    /**
     * Deletes property object from this model.
     *
     * @param property property to delete
     */
    public void deleteProperty(@NotNull Property property) {
        boolean success = generator.getProperty().remove(property);
        if (!success) {
            LOG.error("Property " + property.getName() + ":" + property.getValue() + " was not found in PerfCake JAXB model");
            return;
        }
        fireChangeEvent(PROPERTY_PROPERTY, property, null);
    }

    public void setClazz(String clazz) {
        String old = generator.getClazz();
        generator.setClazz(clazz);
        fireChangeEvent(CLAZZ_PROPERTY, old, clazz);
    }

    public void setThreads(String threads) {
        String old = generator.getThreads();
        generator.setClazz(threads);
        fireChangeEvent(THREADS_PROPERTY, old, threads);
    }
}
