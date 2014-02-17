package org.perfcake.idea.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by miron on 4.2.2014.
 */
public class PerfCakeRunConfiguration extends LocatableConfigurationBase implements PerfCakeRunConfigurationParams {
    private final String SCENARIO_NAME = "SCENARIO_NAME";
    private String scenarioName;

    protected PerfCakeRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new PerfCakeRunConfigurationEditor(this);
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        return null;
    }

    @Override
    public String getScenarioName() {
        return scenarioName;
    }

    @Override
    public void setScenarioName(String name) {
        this.scenarioName = name;
    }

    public static void copyParams(PerfCakeRunConfigurationParams source, PerfCakeRunConfigurationParams target) {
        target.setScenarioName(source.getScenarioName());
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);
        scenarioName = JDOMExternalizerUtil.readField(element, SCENARIO_NAME);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        JDOMExternalizerUtil.writeField(element, SCENARIO_NAME, scenarioName);
        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
    }
}
