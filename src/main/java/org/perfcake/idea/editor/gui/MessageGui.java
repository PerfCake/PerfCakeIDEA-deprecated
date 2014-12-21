package org.perfcake.idea.editor.gui;

import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;

/**
 * Created by miron on 21. 12. 2014.
 */
public class MessageGui extends JPerfCakeIdeaRectangle {

    public MessageGui(String title, ColorType foregroundColor, ColorType backgroundColor) {
        super(title, foregroundColor, backgroundColor);
    }
}
