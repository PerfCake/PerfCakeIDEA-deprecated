package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import com.intellij.openapi.ui.ValidationInfo;
import org.perfcake.idea.editor.dialogs.SenderDialog;
import org.perfcake.idea.model.Sender;

import javax.swing.*;

/**
 * Created by miron on 11. 12. 2014.
 */
public class SenderStep implements Step {

    private SenderDialog senderDialog;
    private NewScenarioWizard newScenarioWizard;

    public SenderStep(Sender sender, NewScenarioWizard newScenarioWizard) {
        this.newScenarioWizard = newScenarioWizard;

        this.senderDialog = new SenderDialog(sender);
    }

    @Override
    public void _init() {
        newScenarioWizard.setTitle(senderDialog.getTitle());
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        ValidationInfo validationInfo = senderDialog.doValidate();
        if (validationInfo != null) {
            throw new CommitStepException(validationInfo.message);
        } else {
            senderDialog.getMockCopy();
        }

    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return senderDialog.getRootPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return senderDialog.getPreferredFocusedComponent();
    }
}
