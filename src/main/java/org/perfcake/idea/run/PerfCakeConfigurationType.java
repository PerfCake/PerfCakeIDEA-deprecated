package org.perfcake.idea.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.Extensions;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.Constants;

import javax.swing.*;

/**
 * Created by miron on 4.2.2014.
 */
public class PerfCakeConfigurationType implements ConfigurationType {
    private static final Logger log = Logger.getInstance(PerfCakeConfigurationType.class);

    private final PerfCakeConfigurationFactory myFactory = new PerfCakeConfigurationFactory(this);


    public static PerfCakeConfigurationType getInstance() {
        for (ConfigurationType configType : Extensions.getExtensions(CONFIGURATION_TYPE_EP)) {
            if (configType instanceof PerfCakeConfigurationType) {
                return (PerfCakeConfigurationType) configType;
            }
        }
        log.error("Unexpected Idea Error: Could not find instance of PerfCakeConfigurationType");
        assert false;
        return null;
    }


    @Override
    public String getDisplayName() {
        return "PerfCake";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "PerfCake Run Configuration";
    }

    @Override
    public Icon getIcon() {
        return Constants.NODE_ICON;
    }

    @NotNull
    @Override
    public String getId() {
        return "PerfCakeConfigurationType";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{myFactory};
    }
}
