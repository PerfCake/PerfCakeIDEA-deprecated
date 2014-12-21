package org.perfcake.idea.editor.swing;

import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;

import java.beans.PropertyChangeListener;

/**
 * Created by miron on 27. 11. 2014.
 */
public class JPerfCakeIdeaEnablingRectangle extends JPerfCakeIdeaRectangle {
    JEnabledCircle enabledCircle;

    public JPerfCakeIdeaEnablingRectangle(String title, Boolean on, ColorType foregroundColor, ColorType backgroundColor) {
        super(title, foregroundColor, backgroundColor);
        enabledCircle = new JEnabledCircle(on == null ? true : on, ColorComponents.getColor(foregroundColor));
        headerPanel.add(enabledCircle, 0);
    }

    public void setOn(Boolean on) {
        if (on == null) {
            enabledCircle.setOn(true);
        } else {
            enabledCircle.setOn(on);
        }

    }

    public Boolean isOn() {
        return enabledCircle.isOn();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        enabledCircle.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        enabledCircle.removePropertyChangeListener(listener);
    }

}
