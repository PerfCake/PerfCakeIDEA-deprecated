package org.perfcake.idea.editor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 15.4.2014.
 */
public class BaseComponent extends JComponent {

    public BaseComponent() {
        //set border
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) { //paint background
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        //get borders
        Insets insets = getInsets();

        //get a copy so we don't have to change g back and can use Graphics2D features
        Graphics2D g2 = (Graphics2D) g.create();

        //change border stroke thickness
        float thickness = 4;
        g2.setStroke(new BasicStroke(thickness));

        g2.setPaint(Color.black);

        //draw round rectangle with respect of insets and add 2 more pixels to borders
        g2.drawRoundRect(insets.right, insets.top, this.getWidth() - (insets.right + insets.left - 1), this.getHeight() - (insets.bottom + insets.top - 1), 30, 30);

        //dispose Graphics
        g2.dispose();
    }
}
