package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import com.intellij.openapi.ui.ValidationInfo;
import org.perfcake.idea.editor.dialogs.GeneratorDialog;
import org.perfcake.idea.model.Generator;

import javax.swing.*;

/**
 * Created by miron on 11. 12. 2014.
 */
public class GeneratorStep implements Step {

    private final NewScenarioWizard newScenarioWizard;
    private GeneratorDialog generatorDialog;

    public GeneratorStep(Generator generator, NewScenarioWizard newScenarioWizard) {
        this.newScenarioWizard = newScenarioWizard;
        generatorDialog = new GeneratorDialog(generator);
    }

    @Override
    public void _init() {
        newScenarioWizard.setTitle(generatorDialog.getTitle());
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        ValidationInfo validationInfo = generatorDialog.doValidate();
        if (validationInfo != null) {
            throw new CommitStepException(validationInfo.message);
        } else {
            generatorDialog.getMockCopy();
        }

    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return generatorDialog.getRootPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return generatorDialog.getPreferredFocusedComponent();
    }

    public boolean isComplete() {
        return generatorDialog.doValidate() == null;
    }
}
