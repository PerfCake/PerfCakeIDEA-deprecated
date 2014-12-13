package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.dialogs.MessageDialog;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 2. 12. 2014.
 */
public class MessageEditAction extends AbstractAction {

    private MessageValidationPair pair;
    private Component parent;

    public MessageEditAction(MessageValidationPair pair, Component parent) {
        super("Edit Message", AllIcons.Actions.Edit);
        setMessageValidationPair(pair);
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Message messageMockCopy = PerfCakeIdeaUtil.runCreateMockCopy(pair.getMessage());
        Validation validationMockCopy = PerfCakeIdeaUtil.runCreateMockCopy(pair.getValidation());
        final MessageValidationPair mockPair = new MessageValidationPair(messageMockCopy, validationMockCopy);

        final MessageDialog messageDialog = new MessageDialog(parent, mockPair);
        boolean ok = messageDialog.showAndGet();

        if (ok) {

            final Project project = messageMockCopy.getModule() == null ? null : messageMockCopy.getModule().getProject();
            final PsiFile containingFile = messageMockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Edit Message", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    pair.getMessage().copyFrom(messageDialog.getMockPair().getMessage());
                    pair.getValidation().copyFrom(messageDialog.getMockPair().getValidation());
                }
            }.execute();
            //update changes in tables
            if (parent instanceof JTable) {
                JTable table = (JTable) parent;
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        }
    }

    public void setMessageValidationPair(MessageValidationPair pair) {
        this.pair = pair;
        if (pair != null) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

}
