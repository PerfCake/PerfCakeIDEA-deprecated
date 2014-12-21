package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import org.perfcake.idea.editor.dialogs.SenderDialog;
import org.perfcake.idea.model.Sender;

import javax.swing.*;

/**
 * Created by miron on 11. 12. 2014.
 */
public class SenderStep implements Step {

    private SenderDialog senderDialog;

    public SenderStep(Sender sender) {

        this.senderDialog = new SenderDialog(sender);
    }

    @Override
    public void _init() {

    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        senderDialog.getMockCopy();
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
        return null;
    }
}
