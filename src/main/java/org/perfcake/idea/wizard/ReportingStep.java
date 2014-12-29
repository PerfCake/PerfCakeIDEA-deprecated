package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.CommitStepException;
import com.intellij.ide.wizard.Step;
import org.perfcake.idea.editor.dialogs.ReportingDialog;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;

/**
 * Created by miron on 12. 12. 2014.
 */
public class ReportingStep implements Step {

    ReportingDialog reportingDialog;
    private NewScenarioWizard newScenarioWizard;

    public ReportingStep(Reporting reporting, NewScenarioWizard newScenarioWizard) {
        this.newScenarioWizard = newScenarioWizard;
        reportingDialog = new ReportingDialog(reporting);
    }

    @Override
    public void _init() {
        newScenarioWizard.setTitle(reportingDialog.getTitle());
    }

    @Override
    public void _commit(boolean finishChosen) throws CommitStepException {
        reportingDialog.getMockCopy();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return reportingDialog.createCenterPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }
}
