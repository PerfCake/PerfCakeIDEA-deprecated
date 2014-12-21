package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.editor.dialogs.MessagesDialog;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.model.Validation;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 3. 12. 2014.
 */
public class MessagesEditAction extends AbstractAction {

    private final Component parent;
    private final Messages messages;

    public MessagesEditAction(Messages messages, Component parent) {
        super("Edit Messages", AllIcons.Actions.Edit);
        this.messages = messages;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (messages.isValid() && messages.getParentOfType(Scenario.class, true) != null) {
            Scenario scenario = messages.getParentOfType(Scenario.class, true);
            if (scenario.isValid() && scenario.getValidation().isValid()) {
                final Validation validation = scenario.getValidation();

                final MessagesValidationPair mockPair = (MessagesValidationPair) new WriteAction() {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        MessagesValidationPair pair = new MessagesValidationPair((Messages) messages.createMockCopy(false),
                                (Validation) validation.createMockCopy(false));
                        result.setResult(pair);
                    }
                }.execute().getResultObject();
                final MessagesDialog messagesDialog = new MessagesDialog(parent, mockPair);
                boolean ok = messagesDialog.showAndGet();

                if (ok) {

                    final Project project = messages.getModule() == null ? null : messages.getModule().getProject();
                    final PsiFile containingFile = messages.getXmlElement() == null ? null : messages.getXmlElement().getContainingFile();

                    new WriteCommandAction(project, "Edit Messages", containingFile) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            messages.copyFrom(messagesDialog.getMockPair().getMessages());
                            validation.copyFrom(messagesDialog.getMockPair().getValidation());
                        }
                    }.execute();
                    //update changes in tables
                    if (parent instanceof JTable) {
                        JTable table = (JTable) parent;
                        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
                    }
                }

            } else throw new RuntimeException("Scenario XML is not valid.");
        } else throw new RuntimeException("Scenario XML is not valid.");
    }
}
