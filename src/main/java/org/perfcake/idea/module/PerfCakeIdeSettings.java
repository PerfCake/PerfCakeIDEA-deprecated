package org.perfcake.idea.module;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.editor.ColorSettingForm;

import javax.swing.*;

/**
 * Created by miron on 21.5.2014.
 */
public class PerfCakeIdeSettings implements Configurable {
    private ColorSettingForm form = new ColorSettingForm();

    @Nls
    @Override
    public String getDisplayName() {
        return "PerfCake IDEA";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return form.getColorPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        form.getColors();
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
