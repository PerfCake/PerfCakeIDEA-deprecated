package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.ValidatorDialog;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 1. 12. 2014.
 */
public class ValidatorAddAction extends AbstractAddAction {

    private Validation validation;
    private Component parent;

    public ValidatorAddAction(Validation validation, Component parent) {
        super("Add Validator");
        this.validation = validation;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final Validation mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(validation);

        final Validator newValidator = mockCopy.addValidator();
        final ValidatorDialog editDialog = new ValidatorDialog(parent, newValidator, Mode.ADD);
        boolean ok = editDialog.showAndGet();
        if (ok) {

            final Project project = mockCopy.getModule() == null ? null : mockCopy.getModule().getProject();
            final PsiFile containingFile = mockCopy.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Validator", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newValidator.copyFrom(editDialog.getMockCopy());
                    validation.copyFrom(mockCopy);
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
