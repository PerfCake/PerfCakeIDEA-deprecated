package org.perfcake.idea.editor.swing;

import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by miron on 27. 11. 2014.
 */
public class JPerfCakeIdeaEnablingRectangle extends JPerfCakeIdeaRectangle implements PropertyChangeListener {
    JEnabledCircle enabledCircle;
    PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public JPerfCakeIdeaEnablingRectangle(String title, Boolean on, ColorType foregroundColor, ColorType backgroundColor) {
        super(title, foregroundColor, backgroundColor);
        setOn(on);
    }

    public void setOn(Boolean on) {
        if (enabledCircle == null) {
            if (on != null) {
                enabledCircle = new JEnabledCircle(on, ColorComponents.getColor(foregroundColor));
                enabledCircle.addPropertyChangeListener(this);
                headerPanel.add(enabledCircle, 0);
            } else {
                return;
            }
        } else {
            if (on == null) {
                headerPanel.remove(enabledCircle);
            } else {
                enabledCircle.setOn(on);
            }
        }

    }

    public Boolean isOn() {
        return (enabledCircle == null) ? null : enabledCircle.isOn();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        changeSupport.firePropertyChange(evt);
    }
}
