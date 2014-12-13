package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.dialogs.ValidationDialog;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 2. 12. 2014.
 */
public class ValidatorAttachAction extends AbstractAddAction {

    private MessageValidationPair pair;
    private Component parent;

    public ValidatorAttachAction(MessageValidationPair pair, Component parent) {
        super("Attach Validator");
        this.pair = pair;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!pair.getMessage().isValid()) throw new RuntimeException("Message xml is invalid.");
        final Validation mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(pair.getValidation());

        final ValidationDialog editDialog = new ValidationDialog(parent, mockCopy, true);
        boolean ok = editDialog.showAndGet();

        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Attach Validator", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    pair.getValidation().copyFrom(editDialog.getMockCopy());
                    String selectedValidator = editDialog.getSelectedValidator();
                    pair.getMessage().addValidatorRef().getId().setStringValue(selectedValidator);
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
