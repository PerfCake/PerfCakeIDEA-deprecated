package org.perfcake.idea.editor.swing;

import org.perfcake.idea.editor.colors.ColorAdjustable;
import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.menu.ActionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by miron on 21.4.2014.
 */
public class JPerfCakeIdeaRectangle extends JRoundedRectangle implements ColorAdjustable {
    protected JPanel headerPanel;
    protected JLabel label;
    protected JPanel contentPanel;

    protected ColorType foregroundColor;
    protected ColorType backGroundColor;


    public JPerfCakeIdeaRectangle(String title, ColorType foregroundColor, ColorType backgroundColor) {
        this.foregroundColor = foregroundColor;
        this.backGroundColor = backgroundColor;

        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        headerPanel.add(label);

        add(headerPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new WrapLayout(FlowLayout.LEADING)) {
            //when the contentPanel does not contain any components, we dont want to have gap between title and
            //bottom border of the component. So the preferred size should be 0,0.
            @Override
            public Dimension getPreferredSize() {
                if (contentPanel.getComponentCount() == 0) {
                    return new Dimension(0, 0);
                }
                return super.getPreferredSize();
            }
        };

        contentPanel.setOpaque(Boolean.FALSE);
        add(contentPanel, BorderLayout.CENTER);

        updateColors();

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.SHIFT_MASK), ActionType.ADD);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_MASK), ActionType.EDIT);
        getInputMap().put(KeyStroke.getKeyStroke("DELETE"), ActionType.DELETE);
    }

    public String getTitle() {
        return label.getText();
    }

    public void setTitle(String title) {
        label.setText(title);
    }

    public void addComponent(Component c) {
        contentPanel.add(c);
    }

    public void removeComponent(Component c) {
        contentPanel.remove(c);
    }

    public void removeAllComponents() {
        contentPanel.removeAll();
    }

    public void updateColors() {
        setForeground(ColorComponents.getColor(foregroundColor));
        setBackground(ColorComponents.getColor(backGroundColor));
    }
}
