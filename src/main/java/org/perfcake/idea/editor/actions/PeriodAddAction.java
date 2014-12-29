package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.PeriodDialog;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Period;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 6. 12. 2014.
 */
public class PeriodAddAction extends AbstractAddAction {

    private final Destination destination;
    private final Component parent;

    public PeriodAddAction(Destination destination, Component parent) {
        super("Add Period");
        this.destination = destination;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Destination mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(destination);

        final Period newPeriod = mockCopy.addPeriod();
        final PeriodDialog addDialog = new PeriodDialog(parent, newPeriod, Mode.ADD);
        boolean ok = addDialog.showAndGet();
        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement() == null ? null : mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Period", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newPeriod.copyFrom(addDialog.getMockCopy());
                    destination.copyFrom(mockCopy);
                }
            }.execute();
            //update changes in tables
            if (parent instanceof JTable) {
                JTable table = (JTable) parent;
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        }
    }
}
