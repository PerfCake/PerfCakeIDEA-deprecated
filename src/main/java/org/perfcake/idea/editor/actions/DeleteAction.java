package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 2. 12. 2014.
 */
public class DeleteAction<T extends DomElement> extends AbstractAction {

    private java.util.List<T> toDelete;
    private Component parent;

    public DeleteAction(String name, java.util.List<T> toDelete, Component parent) {
        super(name, AllIcons.Actions.Delete);
        this.toDelete = toDelete;
        if (toDelete.isEmpty()) setEnabled(false);
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Project project = toDelete.get(0).getModule() == null ? null : toDelete.get(0).getModule().getProject();
        final PsiFile containingFile = toDelete.get(0).getXmlElement().getContainingFile();

        new WriteCommandAction(project, (String) getValue(Action.NAME), containingFile) {
            @Override
            protected void run(@NotNull Result result) throws Throwable {
                for (T element : toDelete) {
                    element.undefine();
                }
            }
        }.execute();
        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        }
    }
}
