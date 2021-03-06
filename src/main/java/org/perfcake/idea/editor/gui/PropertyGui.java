package org.perfcake.idea.editor.gui;

import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;

/**
 * Created by miron on 7. 11. 2014.
 */
public class PropertyGui extends JPerfCakeIdeaRectangle {

    private String name;
    private String value;

    public PropertyGui(String name, String value) {
        super(name + " : " + value, ColorType.PROPERTY_FOREGROUND, ColorType.PROPERTY_BACKGROUND);

        this.name = name;
        this.value = value;
    }

    private String getTitleText() {
        return name + " : " + value;
    }


    public void setName(String name) {
        this.name = name;
        setTitle(getTitleText());
    }


    public void setValue(String value) {
        this.value = value;
        setTitle(getTitleText());
    }
}
