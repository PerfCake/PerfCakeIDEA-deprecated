package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.HeaderDialog;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.model.Header;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 2. 12. 2014.
 */
public class HeaderAddAction extends AbstractAddAction {

    private Message message;
    private Component parent;

    public HeaderAddAction(Message message, Component parent) {
        super("Add Header");
        this.message = message;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Message mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(message);

        final Header newHeader = mockCopy.addHeader();
        final HeaderDialog addDialog = new HeaderDialog(parent, newHeader, Mode.ADD);
        boolean ok = addDialog.showAndGet();
        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement() == null ? null : mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Header", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newHeader.copyFrom(addDialog.getMockCopy());
                    message.copyFrom(mockCopy);
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
