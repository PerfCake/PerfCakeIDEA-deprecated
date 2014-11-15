package org.perfcake.idea.editor.actions;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.model.Property;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 14. 11. 2014.
 */
public class PropertyEditMouseListener extends MouseAdapter {
    private Component parent;
    private Property property;

    public PropertyEditMouseListener(Component parent, Property property) {
        this.parent = parent;
        this.property = property;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && property.isValid()) {
            final Property mockCopy = (Property) new WriteAction() {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    result.setResult(property.createMockCopy(false));
                }
            }.execute().getResultObject();
            final PropertyDialog editDialog = new PropertyDialog(parent, mockCopy);
            boolean ok = editDialog.showAndGet();
            if (ok) {
                new WriteCommandAction((mockCopy).getModule().getProject(), "Edit Property", mockCopy.getXmlElement().getContainingFile()) {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        property.copyFrom(editDialog.getMockCopy());
                    }
                }.execute();
            }
        }
    }
}

