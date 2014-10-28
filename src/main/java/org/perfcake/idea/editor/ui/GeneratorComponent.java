package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.swing.JTitledRoundedRectangle;
import org.perfcake.idea.model.Generator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class GeneratorComponent extends BasicDomElementComponent<Generator> {

    private JTitledRoundedRectangle generatorGui;

    public GeneratorComponent(Generator domElement) {
        super(domElement);

        generatorGui = new JTitledRoundedRectangle(getGuiTitle());

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
