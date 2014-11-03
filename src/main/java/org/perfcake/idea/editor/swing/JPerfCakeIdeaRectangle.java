package org.perfcake.idea.editor.swing;

import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.4.2014.
 * TODO: add PropertyChangeListener implements?
 */
public class JPerfCakeIdeaRectangle extends JRoundedRectangle implements ColorAdjustable {
    protected JLabel label;
    protected JPanel panel;

    private ColorType foregroundColor;
    private ColorType backGroundColor;

    public JPerfCakeIdeaRectangle(String title, ColorType foregroundColor, ColorType backgroundColor) {
        this.foregroundColor = foregroundColor;
        this.backGroundColor = backgroundColor;

        label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        add(label, BorderLayout.NORTH);

        panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.setOpaque(Boolean.FALSE);
        add(panel, BorderLayout.CENTER);

        updateColors();
    }

    public String getTitle() {
        return label.getText();
    }

    public void setTitle(String title) {
        label.setText(title);
    }

    public void addComponent(Component c) {
        panel.add(c);
    }

    public void removeComponent(Component c) {
        panel.remove(c);
    }

    public void removeAllComponents() {
        panel.removeAll();
    }

    public void updateColors() {
        setForeground(ColorComponents.getColor(foregroundColor));
        setBackground(ColorComponents.getColor(backGroundColor));
    }
}
