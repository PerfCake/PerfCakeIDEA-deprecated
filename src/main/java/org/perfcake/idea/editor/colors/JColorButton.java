package org.perfcake.idea.editor.colors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by miron on 1. 11. 2014.
 */
public class JColorButton extends JButton {
    private java.util.List<ChangeListener> changeListenerList = new ArrayList<>();

    public JColorButton(String text, ChangeListener listener) {
        this(text);
        changeListenerList.add(listener);
    }

    public JColorButton(String text) {
        super(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(JColorButton.this, "Choose a color", getBackground());

                if (newColor != null && !newColor.equals(getBackground())) {
                    setBackground(newColor);
                    fireChange();
                }

            }
        });
    }

    private void fireChange() {
        for (ChangeListener listener : changeListenerList) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }
}
