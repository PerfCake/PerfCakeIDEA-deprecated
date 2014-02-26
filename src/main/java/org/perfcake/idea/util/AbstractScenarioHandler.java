package org.perfcake.idea.util;

import com.intellij.openapi.vfs.VirtualFile;
import org.perfcake.model.Scenario;

/**
 * Created by miron on 25.2.2014.
 */
public abstract class AbstractScenarioHandler {
    Scenario scenario;

    protected AbstractScenarioHandler(VirtualFile parentFolder, Scenario scenarioModel) {

        this.scenario = scenarioModel;
    }

    abstract void save();
}
