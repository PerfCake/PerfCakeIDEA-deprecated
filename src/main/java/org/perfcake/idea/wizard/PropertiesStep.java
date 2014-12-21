package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import org.perfcake.idea.editor.dialogs.PropertiesDialog;
import org.perfcake.idea.model.IProperties;

import javax.swing.*;

/**
 * Created by miron on 12. 12. 2014.
 */
public class PropertiesStep implements Step {

    PropertiesDialog propertiesDialog;

    public PropertiesStep(IProperties properties) {
        propertiesDialog = new PropertiesDialog(properties);
    }

    @Override
    public void _init() {

    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        propertiesDialog.getMockCopy();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return propertiesDialog.createCenterPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }
}
