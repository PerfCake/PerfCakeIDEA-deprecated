package org.perfcake.idea.editor.gui;

import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.EditDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;

/**
 * Created by miron on 12.11.2014.
 */
public class GeneratorGui extends JPerfCakeIdeaRectangle implements EditDialog {

    private String threads;
    private String clazz;

    public GeneratorGui(String clazz, String threads) {
        super(clazz + " (" + threads + ")", ColorType.GENERATOR_FOREGROUND, ColorType.GENERATOR_BACKGROUND);

        this.clazz = clazz;
        this.threads = threads;
    }

    private String getTitleText() {
        return clazz + " (" + threads + ")";
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public void invokeDialog() {

    }
}
