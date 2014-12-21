package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import org.perfcake.idea.editor.dialogs.ValidationDialog;
import org.perfcake.idea.model.Validation;

import javax.swing.*;

/**
 * Created by miron on 12. 12. 2014.
 */
public class ValidationStep implements Step {

    ValidationDialog validationDialog;

    public ValidationStep(Validation validation) {
        validationDialog = new ValidationDialog(validation, false);
    }

    @Override
    public void _init() {

    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        validationDialog.getMockCopy();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return validationDialog.createCenterPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }
}
