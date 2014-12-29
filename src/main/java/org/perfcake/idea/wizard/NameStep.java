package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;

import javax.swing.*;

/**
 * Created by miron on 12. 12. 2014.
 */
public class NameStep implements Step {


    private JTextField scenarioTextField;
    private JPanel rootPanel;
    private NewScenarioWizard newScenarioWizard;

    public NameStep(NewScenarioWizard newScenarioWizard) {
        this.newScenarioWizard = newScenarioWizard;
    }

    @Override
    public void _init() {
        newScenarioWizard.setTitle("Specify Scenario Name");
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        if (getScenarioName().isEmpty()) throw new CommitStepException("Please specify scenario file name");
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return rootPanel;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return scenarioTextField;
    }

    public String getScenarioName() {
        return scenarioTextField.getText();
    }
}
