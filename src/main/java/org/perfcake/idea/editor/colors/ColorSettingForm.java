package org.perfcake.idea.editor.colors;

import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 1. 11. 2014.
 */
public class ColorSettingForm implements ChangeListener {
    private static final Logger LOG = Logger.getInstance(ColorSettingForm.class);

    private JPanel colorPanel;
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
    private JButton destinationForeground;
    private JButton destinationBackground;
    private JButton resetToDefaultButton;

    private Map<ColorType, JButton> colorButtons;
    private Boolean changed = Boolean.FALSE;

    public ColorSettingForm() {

        //set colors of buttons according to perfcake component colors
        Map<ColorType, Color> colors = ColorComponents.getColors();
        setButtonColors(colors);


        resetToDefaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorComponents.resetColorsToDefault();
                Map<ColorType, Color> colors = ColorComponents.getColors();
                setButtonColors(colors);
            }
        });
    }

    private void createUIComponents() {
        scenarioBackground = new JColorButton("Background", this);
        propertiesForeground = new JColorButton("Foreground", this);
        propertiesBackground = new JColorButton("Background", this);
        propertyForeground = new JColorButton("Foreground", this);
        propertyBackground = new JColorButton("Background", this);
        generatorForeground = new JColorButton("Foreground", this);
        generatorBackground = new JColorButton("Background", this);
        senderForeground = new JColorButton("Foreground", this);
        senderBackground = new JColorButton("Background", this);
        reportingForeground = new JColorButton("Foreground", this);
        reportingBackground = new JColorButton("Background", this);
        reporterForeground = new JColorButton("Foreground", this);
        reporterBackground = new JColorButton("Background", this);
        destinationForeground = new JColorButton("Foreground", this);
        destinationBackground = new JColorButton("Background", this);
        messagesForeground = new JColorButton("Foreground", this);
        messagesBackground = new JColorButton("Background", this);
        messageForeground = new JColorButton("Foreground", this);
        messageBackground = new JColorButton("Background", this);
        validationForeground = new JColorButton("Foreground", this);
        validationBackground = new JColorButton("Background", this);
        validatorForeground = new JColorButton("Foreground", this);
        validatorBackground = new JColorButton("Background", this);

        colorButtons = new HashMap<>();
        fillColorButtonMap();
    }

    public JPanel getColorPanel() {
        return colorPanel;
    }

    public Map<ColorType, JButton> getColorButtons() {
        return colorButtons;
    }

    public Boolean isChanged() {
        return changed;
    }

    public void resetChanged() {
        changed = Boolean.FALSE;
    }

    private void fillColorButtonMap() {
        colorButtons.put(ColorType.SCENARIO_BACKGROUND, scenarioBackground);
        colorButtons.put(ColorType.PROPERTIES_FOREGROUND, propertiesForeground);
        colorButtons.put(ColorType.PROPERTIES_BACKGROUND, propertiesBackground);
        colorButtons.put(ColorType.PROPERTY_FOREGROUND, propertyForeground);
        colorButtons.put(ColorType.PROPERTY_BACKGROUND, propertyBackground);
        colorButtons.put(ColorType.GENERATOR_FOREGROUND, generatorForeground);
        colorButtons.put(ColorType.GENERATOR_BACKGROUND, generatorBackground);
        colorButtons.put(ColorType.SENDER_FOREGROUND, senderForeground);
        colorButtons.put(ColorType.SENDER_BACKGROUND, senderBackground);
        colorButtons.put(ColorType.REPORTING_FOREGROUND, reportingForeground);
        colorButtons.put(ColorType.REPORTING_BACKGROUND, reportingBackground);
        colorButtons.put(ColorType.REPORTER_FOREGROUND, reporterForeground);
        colorButtons.put(ColorType.REPORTER_BACKGROUND, reporterBackground);
        colorButtons.put(ColorType.DESTINATION_FOREGROUND, destinationForeground);
        colorButtons.put(ColorType.DESTINATION_BACKGROUND, destinationBackground);
        colorButtons.put(ColorType.MESSAGES_FOREGROUND, messagesForeground);
        colorButtons.put(ColorType.MESSAGES_BACKGROUND, messagesBackground);
        colorButtons.put(ColorType.MESSAGE_FOREGROUND, messageForeground);
        colorButtons.put(ColorType.MESSAGE_BACKGROUND, messageBackground);
        colorButtons.put(ColorType.VALIDATION_FOREGROUND, validationForeground);
        colorButtons.put(ColorType.VALIDATION_BACKGROUND, validationBackground);
        colorButtons.put(ColorType.VALIDATOR_FOREGROUND, validatorForeground);
        colorButtons.put(ColorType.VALIDATOR_BACKGROUND, validatorBackground);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        changed = Boolean.TRUE;
    }

    public void setButtonColors(Map<ColorType, Color> colors) {
        Map<ColorType, JButton> butons = getColorButtons();
        for (Map.Entry<ColorType, JButton> buttonEntry : butons.entrySet()) {
            buttonEntry.getValue().setBackground(colors.get(buttonEntry.getKey()));
        }
    }
}


