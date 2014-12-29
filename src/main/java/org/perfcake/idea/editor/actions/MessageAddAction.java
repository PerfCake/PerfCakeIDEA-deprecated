package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.editor.dialogs.MessageDialog;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 3. 12. 2014.
 */
public class MessageAddAction extends AbstractAddAction {


    private final MessagesValidationPair pair;
    private final Component parent;

    public MessageAddAction(MessagesValidationPair pair, Component parent) {
        super("Add Message");
        this.pair = pair;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final Messages messagesMockCopy = PerfCakeIdeaUtil.runCreateMockCopy(pair.getMessages());
        final Message newMessage = messagesMockCopy.addMessage();
        Validation validationMockCopy = PerfCakeIdeaUtil.runCreateMockCopy(pair.getValidation());

        MessageValidationPair newMockPair = new MessageValidationPair(newMessage, validationMockCopy);
        final MessageDialog editDialog = new MessageDialog(parent, newMockPair, Mode.ADD);
        boolean ok = editDialog.showAndGet();
        if (ok) {

            final Project project = messagesMockCopy.getModule() == null ? null : messagesMockCopy.getModule().getProject();
            final PsiFile containingFile = messagesMockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Message", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    MessageValidationPair editDialogMockPair = editDialog.getMockPair();

                    newMessage.copyFrom(editDialogMockPair.getMessage());
                    pair.getValidation().copyFrom(editDialogMockPair.getValidation());
                    pair.getMessages().copyFrom(messagesMockCopy);
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
