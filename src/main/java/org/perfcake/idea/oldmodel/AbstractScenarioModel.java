package org.perfcake.idea.oldmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by miron on 22.4.2014.
 */
public abstract class AbstractScenarioModel {
    protected PropertyChangeSupport propertyChangeSupport;

    protected AbstractScenarioModel() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void fireChangeEvent(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
