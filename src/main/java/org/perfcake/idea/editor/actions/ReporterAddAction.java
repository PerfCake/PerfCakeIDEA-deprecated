package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.ReporterDialog;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 4. 12. 2014.
 */
public class ReporterAddAction extends AbstractAddAction {


    private Reporting reporting;
    private Component parent;

    public ReporterAddAction(Reporting reporting, Component parent) {
        super("Add Reporter");
        this.reporting = reporting;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Reporting mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(reporting);
        final Reporter newReporter = mockCopy.addReporter();
        final ReporterDialog addDialog = new ReporterDialog(parent,
                newReporter, Mode.ADD);
        boolean ok = addDialog.showAndGet();
        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(
                    project, "Add Reporter", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newReporter.copyFrom(addDialog.getMockCopy());
                    reporting.copyFrom(mockCopy);
                }
            }.execute();
        }
        //update changes in tables
        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        }
    }
}
