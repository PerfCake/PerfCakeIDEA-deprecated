package org.perfcake.idea.editor.gui;

import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;

/**
 * Created by miron on 9. 11. 2014.
 */
public class PropertiesGui extends JPerfCakeIdeaRectangle {

    private static final String PROPERTIES_TITLE = "Scenario Properties";
    //private List<Property> properties;

    public PropertiesGui() {//List<Property> properties) {
        super(PROPERTIES_TITLE, ColorType.PROPERTIES_FOREGROUND, ColorType.PROPERTIES_BACKGROUND);
        //this.properties = properties;
    }

//    public List<Property> getMockPair() {
//        return properties;
//    }

//    public void setProperties(List<Property> properties) {
//        this.properties = properties;
//    }

}
