package org.perfcake.idea.editor.colors;


import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.components.panels.Wrapper;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.EditorPanel;
import org.perfcake.idea.editor.components.ScenarioPanel;
import org.perfcake.idea.editor.swing.ColorAdjustable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 30. 10. 2014.
 */
public class ColorComponents {

    private static final Logger LOG = Logger.getInstance(ColorComponents.class);

    private static PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();


    /**
     * Apply current colors to all open Scenario Editors
     */
    private static void applyColors() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        for (Project p : openProjects) {
            FileEditor[] allEditors = FileEditorManager.getInstance(p).getAllEditors();
            for (FileEditor e : allEditors) {
                //all scenario editors are wrapped by Intellij
                if (e.getName().equals("Designer") && e.getComponent() instanceof Wrapper) {
                    JComponent targetComponent = ((Wrapper) e.getComponent()).getTargetComponent();
                    if (targetComponent instanceof EditorPanel) {
                        ScenarioPanel scenarioPanel = ((EditorPanel) targetComponent).getScenarioPanel();
                        updateColorAdjustableTree(new Component[]{scenarioPanel});
                    }
                }
            }
        }
    }

    /**
     * For every component in array, which is of type ColorAdjustable and JComponent, updates colors and recursively for all children.
     *
     * @param components components array to update
     */
    private static void updateColorAdjustableTree(Component[] components) {
        for (Component c : components) {
            if (c instanceof JComponent) {
                JComponent toUpdate = (JComponent) c;
                if (c instanceof ColorAdjustable) ((ColorAdjustable) toUpdate).updateColors();
                synchronized (c.getTreeLock()) {
                    updateColorAdjustableTree(toUpdate.getComponents());
                }
            }
        }
    }

    /**
     * Sets new color for given ColorType
     *
     * @param colorType specifies for what to set the color
     * @param color     to set
     */
    private static void setColor(ColorType colorType, Color color) {
        propertiesComponent.setValue(colorType.name(), colorToString(color));
    }

    /**
     * Gets current color for specified colorType
     *
     * @param colorType for which to get a color
     * @return color for specified colorType
     */
    //@NotNull
    public static Color getColor(@NotNull ColorType colorType) {
        if (!isColoringOn()) return null;
        if (propertiesComponent.isValueSet(colorType.name())) {
            String color = propertiesComponent.getValue(colorType.name());
            return parseColor(color);
        } else {
            return getDefaultColor(colorType);
        }

    }

    public static boolean isColoringOn() {
        return propertiesComponent.getBoolean("coloring", false);
    }

    public static void setColoringOn(boolean on) {
        propertiesComponent.setValue("coloring", Boolean.toString(on));
    }

    /**
     * Gets current colors for all colorTypes
     *
     * @return Map of ColorType and it's Color
     */
    public static Map<ColorType, Color> getColors() {
        Map<ColorType, Color> colors = new HashMap<>();

        for (ColorType colorType : ColorType.values()) {
            if (propertiesComponent.isValueSet(colorType.name())) {
                String color = propertiesComponent.getValue(colorType.name());
                colors.put(colorType, parseColor(color));
            } else {
                colors.put(colorType, getDefaultColor(colorType));
            }
        }
        return colors;

    }

    /**
     * Sets new colors for all ColorTypes (PerfCake GUI components) and updates them in current open editors.
     *
     * @param chosenColors Map of ColorType and chosen Color for all ColorTypes
     */
    public static void setColors(@NotNull Map<ColorType, Color> chosenColors) {
        for (Map.Entry<ColorType, Color> entry : chosenColors.entrySet()) {
            setColor(entry.getKey(), entry.getValue());
        }
        applyColors();
    }

    /**
     * Gets default color for specified ColorType. Default colors are intended for initial state and resetting purposes.
     *
     * @param colorType specifies for what to set the color
     * @return default color
     */
    private static Color getDefaultColor(ColorType colorType) {
        switch (colorType) {
            case SCENARIO_BACKGROUND:
                return parseColor("255,255,255");
            case GENERATOR_FOREGROUND:
                return parseColor("12,188,0");
            case GENERATOR_BACKGROUND:
                return parseColor("255,255,255");
            case MESSAGES_FOREGROUND:
                return parseColor("255,136,0");
            case MESSAGES_BACKGROUND:
                return parseColor("255,255,255");
            case MESSAGE_FOREGROUND:
                return parseColor("255,136,0");
            case MESSAGE_BACKGROUND:
                return parseColor("255,255,255");
            case REPORTING_FOREGROUND:
                return parseColor("206,23,0");
            case REPORTING_BACKGROUND:
                return parseColor("255,255,255");
            case REPORTER_FOREGROUND:
                return parseColor("206,23,0");
            case REPORTER_BACKGROUND:
                return parseColor("255,255,255");
            case DESTINATION_FOREGROUND:
                return parseColor("206,23,0");
            case DESTINATION_BACKGROUND:
                return parseColor("255,255,255");
            case SENDER_FOREGROUND:
                return parseColor("16,0,220");
            case SENDER_BACKGROUND:
                return parseColor("255,255,255");
            case VALIDATION_FOREGROUND:
                return parseColor("220,0,255");
            case VALIDATION_BACKGROUND:
                return parseColor("255,255,255");
            case VALIDATOR_FOREGROUND:
                return parseColor("220,0,255");
            case VALIDATOR_BACKGROUND:
                return parseColor("255,255,255");
            case PROPERTIES_FOREGROUND:
                return parseColor("127,127,127");
            case PROPERTIES_BACKGROUND:
                return parseColor("255,255,255");
            case PROPERTY_FOREGROUND:
                return parseColor("127,127,127");
            case PROPERTY_BACKGROUND:
                return parseColor("255,255,255");
        }
        LOG.error("Error getting default color: Unknown ColorType");
        throw new RuntimeException("Error getting default color: Unknown ColorType");
    }

    /**
     * Gets all default colors . Default colors are intended for initial state and resetting purposes.
     *
     * @return all default colors
     */
    public static Map<ColorType, Color> getDefaultColors() {
        Map<ColorType, Color> colors = new HashMap<>();

        for (ColorType colorType : ColorType.values()) {
            colors.put(colorType, getDefaultColor(colorType));
        }
        return colors;
    }



    /**
     * Parses String representation of a Color RGB values and creates Color accordng to it.
     *
     * @param color String representation of a color to parse. Format is: RRR,GGG,BBB
     * @return created Color object
     */
    private static Color parseColor(String color) {
        String[] rgb = color.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        return new Color(r, g, b);
    }

    /**
     * Returns String representation of Color RGB values. Ignores Alpha value.
     *
     * @param color for which to get a String represenation.
     * @returnString representation
     */
    private static String colorToString(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
