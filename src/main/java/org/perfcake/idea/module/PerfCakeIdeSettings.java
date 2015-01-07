package org.perfcake.idea.module;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorSettingForm;
import org.perfcake.idea.editor.colors.ColorType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 21.5.2014. This class adds PerfCake settings to Idea settings.
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
        return form.isChanged();
    }

    @Override
    public void apply() throws ConfigurationException {
        Map<ColorType, JButton> colorButtons = form.getColorButtons();

        //fill color map for color apply
        Map<ColorType, Color> colors = new HashMap<>();
        for (Map.Entry<ColorType, JButton> entry : colorButtons.entrySet()) {
            colors.put(entry.getKey(), entry.getValue().getBackground());
        }
        //apply colors
        ColorComponents.setColors(colors);
        //reset form is changed status, because all changes are applied
        form.resetChanged();

    }

    @Override
    public void reset() {
        Map<ColorType, Color> colors = ColorComponents.getColors();
        form.setButtonColors(colors);
        form.resetChanged();
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }
}
