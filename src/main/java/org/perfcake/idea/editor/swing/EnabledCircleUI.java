package org.perfcake.idea.editor.swing;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

/**
 * Created by miron on 21. 11. 2014.
 */
public class EnabledCircleUI extends PanelUI {


    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        //compute component drawing area with respect to insets
        Rectangle area = computeArea(c);

        //get a copy so we don't have to change g back and can use Graphics2D features
        Graphics2D g2 = (Graphics2D) g.create();

        //allow antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //set background color
        g2.setPaint(c.getBackground());
        g2.fillOval(area.x, area.y, 10, 10);

        //set foreground color
        g2.setPaint(c.getForeground());

        //change border stroke thickness
        g2.setStroke(new BasicStroke(1));

        //draw round rectangle with respect of insets
        g2.drawOval(area.x, area.y, 9, 9);

        //dispose Graphics
        g2.dispose();
    }

    private Rectangle computeArea(JComponent c) {
        //get borders
        Insets insets = c.getInsets();
        //System.out.print("Insets: " + insets.left + " " + insets.top + " " + insets.left + " " + insets.bottom);

        //compute component size
        int width = c.getWidth() - (insets.right + insets.left);
        int height = c.getHeight() - (insets.top + insets.bottom);

        return new Rectangle(insets.left, insets.top, width, height);
    }

    @Override
    public boolean contains(JComponent c, int x, int y) {
        Rectangle area = computeArea(c);
        return area.contains(x, y);
    }
}
