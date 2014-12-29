package org.perfcake.idea.module;

import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;

/**
 * Created by miron on 23. 12. 2014.
 */
public class PerfCakeIdeaStandardResourceProvider implements StandardResourceProvider {
    /**
     * This method registers PerfCake schema to Intellij Idea
     *
     * @param registrar
     */
    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource("urn:perfcake:scenario:3.0", "/perfcake-scenario-3.0.xsd", PerfCakeIdeaStandardResourceProvider.class);
    }
}
