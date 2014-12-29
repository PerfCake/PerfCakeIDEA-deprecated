package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.DialogFactory;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.MyDialogWrapper;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 1. 12. 2014.
 */
public class EditAction<T extends DomElement> extends AbstractAction {

    private T domElement;
    private Component parent;

    public EditAction(String name, T domElement, Component parent) {
        super(name, AllIcons.Actions.Edit);
        setDomElement(domElement);
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final T mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(domElement);
        final MyDialogWrapper dialog = DialogFactory.createDialogForElement(mockCopy, parent, Mode.EDIT);
        boolean ok = dialog.showAndGet();

        if (ok) {
            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement() == null ? null : mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, (String) getValue(Action.NAME), containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    domElement.copyFrom(dialog.getMockCopy());
                }
            }.execute();
        }
        //update changes in tables
        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        }
    }

    public void setDomElement(T domElement) {
        if (domElement == null) {
            setEnabled(false);
        } else {
            this.domElement = domElement;
            setEnabled(true);
        }
    }
}
