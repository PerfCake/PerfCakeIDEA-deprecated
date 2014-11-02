package org.perfcake.idea.editor.editor;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 1. 11. 2014.
 */
public class ColorSettingForm {
    private JPanel colorPanel;
    private JButton scenarioForeground;
    private JButton scenarioBackground;
    private JButton propertiesForeground;
    private JButton propertiesBackground;
    private JButton propertyForeground;
    private JButton propertyBackground;
    private JButton generatorForeground;
    private JButton generatorBackground;
    private JButton senderForeground;
    private JButton senderBackground;
    private JButton reportingForeground;
    private JButton reportingBackground;
    private JButton reporterForeground;
    private JButton reporterBackground;
    private JButton messagesForeground;
    private JButton messagesBackground;
    private JButton messageForeground;
    private JButton messageBackground;
    private JButton validationForeground;
    private JButton validationBackground;
    private JButton validatorForeground;
    private JButton validatorBackground;

    private Boolean modified;


    private void createUIComponents() {
        scenarioForeground = new JColorButton("Foreground");
        scenarioBackground = new JColorButton("Background");
        propertiesForeground = new JColorButton("Foreground");
        propertiesBackground = new JColorButton("Background");
        propertyForeground = new JColorButton("Foreground");
        propertyBackground = new JColorButton("Background");
        generatorForeground = new JColorButton("Foreground");
        generatorBackground = new JColorButton("Background");
        senderForeground = new JColorButton("Foreground");
        senderBackground = new JColorButton("Background");
        reportingForeground = new JColorButton("Foreground");
        reportingBackground = new JColorButton("Background");
        reporterForeground = new JColorButton("Foreground");
        reporterBackground = new JColorButton("Background");
        messagesForeground = new JColorButton("Foreground");
        messagesBackground = new JColorButton("Background");
        messageForeground = new JColorButton("Foreground");
        messageBackground = new JColorButton("Background");
        validationForeground = new JColorButton("Foreground");
        validationBackground = new JColorButton("Background");
        validatorForeground = new JColorButton("Foreground");
        validatorBackground = new JColorButton("Background");
    }

    public JPanel getColorPanel() {
        return colorPanel;
    }

    public Map<String, Color> getColors(){
        Map<String, Color> colors = new HashMap<>();
        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType() == JButton.class){
                try {
                    colors.put(field.getName(), ((JColorButton)field.get(this)).getBackground());
                } catch (IllegalAccessException e) {
                    //TODO log?
                }
            }
        }
        return colors;
    }
}
