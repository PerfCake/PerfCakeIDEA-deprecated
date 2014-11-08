package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.model.Run;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class RunComponent extends BasicDomElementComponent<Run> {

    private JLabel runGui;

    public RunComponent(Run domElement) {
        super((Run) domElement.createStableCopy());
        runGui = new JLabel(getGuiText());

    }

    @Override
    public JComponent getComponent() {
        return runGui;
    }

    @Override
    public void reset() {
        super.reset();
        runGui.setText(getGuiText());
    }

    private String getGuiText() {
        return myDomElement.getType().getStringValue() + " : " + myDomElement.getValue().getStringValue();
    }
}
