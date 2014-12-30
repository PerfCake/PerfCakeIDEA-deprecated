package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import com.intellij.psi.PsiDirectory;

import javax.swing.*;

/**
 * Created by miron on 12. 12. 2014.
 */
public class NameStep implements Step {


    private JTextField scenarioTextField;
    private JPanel rootPanel;
    private NewScenarioWizard newScenarioWizard;
    private PsiDirectory scenariosDirPsi;

    public NameStep(NewScenarioWizard newScenarioWizard, PsiDirectory scenariosDirPsi) {
        this.newScenarioWizard = newScenarioWizard;
        this.scenariosDirPsi = scenariosDirPsi;
    }


    @Override
    public void _init() {
        newScenarioWizard.setTitle("Specify Scenario Name");
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        if (getScenarioName().isEmpty()) throw new CommitStepException("Please specify scenario file name");
        if (scenariosDirPsi.findFile(NewScenarioAction.getNameWithExtension(scenarioTextField.getText())) != null)
            throw new CommitStepException("File with this name already exists. Please specify another name.");

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
