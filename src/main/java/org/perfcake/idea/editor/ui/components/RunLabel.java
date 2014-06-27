package org.perfcake.idea.editor.ui.components;

import org.perfcake.idea.model.RunModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 9.6.2014.
 */
public class RunLabel extends JLabel implements PropertyChangeListener {

    private RunModel model;

    public RunLabel(RunModel model) {
        super(model.toString());
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public RunModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setText(model.toString());
    }
}
