package org.perfcake.idea.module;

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

    public MessagesStep(MessagesValidationPair pair) {
        messagesDialog = new MessagesDialog(pair);
    }

    @Override
    public void _init() {

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
