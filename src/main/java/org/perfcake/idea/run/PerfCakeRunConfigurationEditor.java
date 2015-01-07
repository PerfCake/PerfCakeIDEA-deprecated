package org.perfcake.idea.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by miron on 17.2.2014.
 * This class is handling data from run configuration GUI.
 */
public class PerfCakeRunConfigurationEditor extends SettingsEditor<PerfCakeRunConfiguration> {
    private PerfCakeRunConfigurationForm myForm;

    public PerfCakeRunConfigurationEditor(PerfCakeRunConfiguration configuration) {
        myForm = new PerfCakeRunConfigurationForm(configuration);
    }


    @Override
    protected void resetEditorFrom(PerfCakeRunConfiguration s) {
        PerfCakeRunConfiguration.copyParams(s, myForm);

    }

    @Override
    protected void applyEditorTo(PerfCakeRunConfiguration s) throws ConfigurationException {
        PerfCakeRunConfiguration.copyParams(myForm, s);

    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myForm.getRootPanel();
    }
}
