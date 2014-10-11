package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.model.Scenario;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioComponent extends BasicDomElementComponent<Scenario> {

    public ScenarioComponent(Scenario domElement) {
        super(domElement);
    }

    @Override
    public JComponent getComponent() {
        return null;
    }
}
