package org.perfcake.idea.editor.ui;

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
        super(domElement);

        scenarioPanel = new ScenarioPanel(this);
        editorPanel = new EditorPanel(new JPanel(), scenarioPanel);
    }


    @Override
    public JComponent getComponent() {
        return editorPanel;
    }

    public ScenarioPanel getScenarioPanel() {
        return scenarioPanel;
    }
}
