package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.model.Scenario;

import javax.swing.*;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioComponent extends BasicDomElementComponent<Scenario> {
    private ScenarioPanel scenarioPanel;
    private EditorPanel editorPanel;

    public ScenarioComponent(Scenario domElement) {
        super((Scenario) domElement.createStableCopy());

        scenarioPanel = new ScenarioPanel(this);
        editorPanel = new EditorPanel(new ToolbarPanel(), scenarioPanel);
    }


    @Override
    public JComponent getComponent() {
        return editorPanel;
    }

    public ScenarioPanel getScenarioPanel() {
        return scenarioPanel;
    }
}
