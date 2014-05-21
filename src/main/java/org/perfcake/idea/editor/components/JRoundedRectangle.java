package org.perfcake.idea.editor.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 15.4.2014.
 */
public class JRoundedRectangle extends JPanel {
    private static final String uiClassID = "RoundedRectangleUI";
    private Boolean selected = Boolean.FALSE;

    public JRoundedRectangle() {
        //set default layout
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new BorderLayout());
        //this component is rounded, so we will set opacity to false to see background in corners
        setOpaque(Boolean.FALSE);
    }

    public String getUIClassID() {
        return uiClassID;
    }

    public RoundedRectangleUI getUI() {
        return (RoundedRectangleUI) ui;
    }

    public void setUI(RoundedRectangleUI ui) {
        super.setUI(ui);
    }

    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            setUI((RoundedRectangleUI) UIManager.getUI(this));
        } else {
            //fallback if UIDefaults table was not set
            setUI(new RoundedRectangleUI());
        }
    }

    //TODO write to model?
    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void invokeDialog() {
    }

}
