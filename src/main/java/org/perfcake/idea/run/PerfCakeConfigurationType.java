package org.perfcake.idea.run;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import org.perfcake.idea.Constants;

import javax.swing.*;

/**
 * Created by miron on 4.2.2014.
 */
public class PerfCakeConfigurationType extends ConfigurationTypeBase {
    private static final String ID = "PERFCAKE_CONFIGURATION";
    private static final String DISPLAY_NAME = "PerfCake";
    private static final String DESCRIPTION = "PerfCake Description";
    private static final Icon ICON = Constants.NODE_ICON;

    protected PerfCakeConfigurationType() {
        super(ID, DISPLAY_NAME, DESCRIPTION, ICON);
        addFactory(new PerfCakeConfigurationFactory(this));
    }

}
