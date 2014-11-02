package org.perfcake.idea.editor.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by miron on 1. 11. 2014.
 */
public class JColorButton extends JButton {

    private Color color = Color.lightGray;
    private Boolean modified;

    public JColorButton(String text, Boolean modified) {
        super(text);
        this.modified = modified;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = JColorChooser.showDialog(JColorButton.this, "Choose a color", color);

                if(color == null){
                    color = Color.lightGray;
                }
                setBackground(color);
            }
        });
    }
}
