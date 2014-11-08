package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Generator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class GeneratorComponent extends BasicDomElementComponent<Generator> {

    private JPerfCakeIdeaRectangle generatorGui;

    public GeneratorComponent(Generator domElement) {
        super((Generator) domElement.createStableCopy());

        generatorGui = new JPerfCakeIdeaRectangle(getGuiTitle(), ColorType.GENERATOR_FOREGROUND, ColorType.GENERATOR_BACKGROUND);

        RunComponent runComponent = new RunComponent(domElement.getRun());
        generatorGui.addComponent(runComponent.getComponent());
        addComponent(runComponent);
    }

    @Override
    public JComponent getComponent() {
        return generatorGui;
    }

    @Override
    public void reset() {
        super.reset();
        generatorGui.setTitle(getGuiTitle());
    }

    private String getGuiTitle() {
        return myDomElement.getClazz().getStringValue() + " (" + myDomElement.getThreads().getStringValue() + ")";
    }
}
