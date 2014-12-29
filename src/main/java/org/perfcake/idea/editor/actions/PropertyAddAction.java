package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.model.IProperties;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 29. 11. 2014.
 */
public class PropertyAddAction extends AbstractAction {

    private Component parent;
    private IProperties properties;

    public PropertyAddAction(IProperties properties, Component parent) {
        super("Add Property", AllIcons.General.Add);
        this.properties = properties;
        this.parent = parent;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final IProperties mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(properties);

        final Property newProperty = mockCopy.addProperty();
        final PropertyDialog editDialog = new PropertyDialog(parent, newProperty, Mode.ADD);
        boolean ok = editDialog.showAndGet();
        if (ok) {
            final Project project = newProperty.getModule() == null ? null : newProperty.getModule().getProject();
            final PsiFile containingFile = newProperty.getXmlElement() == null ? null : newProperty.getXmlElement().getContainingFile();

            new WriteCommandAction(project, "Add Property", containingFile) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newProperty.copyFrom(editDialog.getMockCopy());
                    properties.copyFrom(mockCopy);
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
