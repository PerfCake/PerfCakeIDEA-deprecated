package org.perfcake.idea.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miron on 4.2.2014.
 * @see super
 */
public class PerfCakeConfigurationFactory extends ConfigurationFactory {

    protected PerfCakeConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new PerfCakeRunConfiguration(project, this, "PerfCake Default");
    }
}
