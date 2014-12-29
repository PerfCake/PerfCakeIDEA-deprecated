package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.editor.dialogs.MessagesDialog;

import javax.swing.*;

/**
 * Created by miron on 11. 12. 2014.
 */
public class MessagesStep implements Step {

    MessagesDialog messagesDialog;
    private NewScenarioWizard newScenarioWizard;

    public MessagesStep(MessagesValidationPair pair, NewScenarioWizard newScenarioWizard) {
        this.newScenarioWizard = newScenarioWizard;
        messagesDialog = new MessagesDialog(pair);
    }

    @Override
    public void _init() {
        newScenarioWizard.setTitle(messagesDialog.getTitle());
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        messagesDialog.getMockPair();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return messagesDialog.getRootPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }
}
