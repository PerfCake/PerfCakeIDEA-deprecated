package org.perfcake.idea.editor.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        //add selection support
        setFocusable(true);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                select();
            }

            @Override
            public void focusLost(FocusEvent e) {
                deselect();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
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

    private void select() {
        if (!selected) {
            selected = Boolean.TRUE;
            repaint();
        }
    }

    private void deselect() {
        if (selected) {
            selected = Boolean.FALSE;
            repaint();
        }
    }

    public Boolean isSelected() {
        return selected;
    }


}
