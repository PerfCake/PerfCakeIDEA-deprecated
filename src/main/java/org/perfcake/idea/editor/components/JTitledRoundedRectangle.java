package org.perfcake.idea.editor.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.4.2014.
 * TODO: add PropertyChangeListener implements?
 */
public class JTitledRoundedRectangle extends JRoundedRectangle {
    protected JLabel label;
    protected JPanel panel;

    public JTitledRoundedRectangle(String title) {
        label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        add(label, BorderLayout.NORTH);

        panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.setOpaque(Boolean.FALSE);
        add(panel, BorderLayout.CENTER);


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
}
