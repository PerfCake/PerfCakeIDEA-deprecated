package org.perfcake.idea.editor.swing;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by miron on 17.4.2014.
 */
public class RoundedRectangleUI extends PanelUI {
    private final int arcWidth = 10;
    private final int arcHeight = 10;
    private float thickness = 1;

    @Override
    public void installUI(JComponent c) {
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        thickness = ((JRoundedRectangle) c).isSelected() ? 3 : 1;

        //compute component drawing area with respect to insets
        Rectangle area = computeArea(c);

        //get a copy so we don't have to change g back and can use Graphics2D features
        Graphics2D g2 = (Graphics2D) g.create();

        //allow antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //set background color
        g2.setPaint(c.getBackground());
        g2.fillRoundRect(area.x, area.y, area.width, area.height, arcWidth, arcHeight);

        //set foreground color
        g2.setPaint(c.getForeground());

        //change border stroke thickness
        g2.setStroke(new BasicStroke(thickness));

        //draw round rectangle with respect of insets
        g2.drawRoundRect(area.x, area.y, area.width - 1, area.height - 1, arcWidth, arcHeight);

        //dispose Graphics
        g2.dispose();
    }

    private Rectangle computeArea(JComponent c) {
        //get borders
        Insets insets = c.getInsets();

        //compute component size
        int width = c.getWidth() - (insets.right + insets.left);
        int height = c.getHeight() - (insets.top + insets.bottom);

        return new Rectangle(insets.left, insets.top, width, height);
    }

    @Override
    public boolean contains(JComponent c, int x, int y) {
        Rectangle area = computeArea(c);
        return (new RoundRectangle2D.Double(area.x, area.y, area.width, area.height, arcWidth, arcHeight)).contains(x, y);
    }
}

