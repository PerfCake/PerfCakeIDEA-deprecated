package org.perfcake.idea.editor.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21. 11. 2014.
 */
public class JEnabledCircle extends JPanel {
    private static final String uiClassID = "EnabledCircleUI";
    private Boolean on = Boolean.FALSE;

    public JEnabledCircle() {
        //set default layout
        setLayout(new BorderLayout());
        //this component is rounded, so we will set opacity to false to see background in corners
        setOpaque(Boolean.FALSE);
        setBackground(Color.green);
        setForeground(Color.blue);
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

    private void turnOn() {
        if (!on) {
            on = Boolean.TRUE;
            repaint();
        }
    }

    private void turnOff() {
        if (on) {
            on = Boolean.FALSE;
            repaint();
        }
    }

    public Boolean isOn() {
        return on;
    }
}
