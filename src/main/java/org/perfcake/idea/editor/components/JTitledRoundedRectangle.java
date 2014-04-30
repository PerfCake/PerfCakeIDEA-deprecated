package org.perfcake.idea.editor.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.4.2014.
 */
public class JTitledRoundedRectangle extends JRoundedRectangle {
    protected JLabel label;
    //protected JPanel panel;

    public JTitledRoundedRectangle(String title) {
        label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        //panel = new JPanel(new FlowLayout());
        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        //add(panel);


    }
}
