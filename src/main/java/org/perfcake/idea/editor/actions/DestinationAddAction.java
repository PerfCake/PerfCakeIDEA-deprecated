package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.DestinationDialog;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 5. 12. 2014.
 */
public class DestinationAddAction extends AbstractAddAction {

    private final Reporter reporter;
    private final Component parent;

    public DestinationAddAction(Reporter reporter, Component parent) {
        super("Add Destination");
        this.reporter = reporter;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Reporter mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(reporter);
        final Destination newDestination = mockCopy.addDestination();
        final DestinationDialog editDialog = new DestinationDialog(parent, newDestination, Mode.ADD);
        boolean ok = editDialog.showAndGet();
        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Destination", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newDestination.copyFrom(editDialog.getMockCopy());
                    reporter.copyFrom(mockCopy);
                }
            }.execute();
        } //update changes in tables
        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        }
    }
}
