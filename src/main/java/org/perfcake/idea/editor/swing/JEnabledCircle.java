package org.perfcake.idea.editor.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by miron on 21. 11. 2014.
 */
public class JEnabledCircle extends JPanel {
    private static final String uiClassID = "EnabledCircleUI";
    private boolean on;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public JEnabledCircle(boolean on, Color foreground) {
        //set default layout
        setLayout(new BorderLayout());
        //this component is rounded, so we will set opacity to false to see background in corners
        setOpaque(Boolean.FALSE);

        setOn(on);
        setForeground(foreground);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    svitch();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


    public String getUIClassID() {
        return uiClassID;
    }

    public EnabledCircleUI getUI() {
        return (EnabledCircleUI) ui;
    }

    public void setUI(EnabledCircleUI ui) {
        super.setUI(ui);
    }

    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            setUI((EnabledCircleUI) UIManager.getUI(this));
        } else {
            //fallback if UIDefaults table was not set
            setUI(new EnabledCircleUI());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }

    private void svitch() {
        setOn(!on);
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        boolean oldValue = this.on;
        this.on = on;
        if (on) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.RED);
        }
        this.propertyChangeSupport.firePropertyChange("on", oldValue, on);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
