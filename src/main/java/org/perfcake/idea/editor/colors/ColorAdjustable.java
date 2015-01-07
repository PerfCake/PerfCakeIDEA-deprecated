package org.perfcake.idea.editor.colors;

/**
 * Created by miron on 2.11.2014.
 * This interface is intended for Swing components,
 * which wants to support coloring via ColorComponents API.
 */
public interface ColorAdjustable {

    /**
     * This method is called from ColorComponents to update colors according to user preferences.
     * Implementation should get the current colors from ColorComponents method getColors.
     */
    void updateColors();
}
