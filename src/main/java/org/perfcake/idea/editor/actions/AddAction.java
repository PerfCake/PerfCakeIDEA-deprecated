package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.DialogFactory;
import org.perfcake.idea.editor.dialogs.MyDialogWrapper;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 1. 12. 2014.
 */
public class AddAction<T extends DomElement> extends AbstractAction {

    private T domElement;
    private Component parent;

    public AddAction(String name, T domElement, Component parent) {
        super(name, AllIcons.General.Add);
        this.domElement = domElement;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final T mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(domElement);

        final MyDialogWrapper dialog = DialogFactory.createDialogForElement(domElement, parent);
        boolean ok = dialog.showAndGet();

        if (ok) {
            new WriteCommandAction(mockCopy.getModule().getProject(), (String) getValue(Action.NAME), mockCopy.getXmlElement().getContainingFile()) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    mockCopy.copyFrom(dialog.getMockCopy());
                }
            }.execute();
        }
        //
        if (parent instanceof JTable) {
            JTable table = (JTable) parent;
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        }
    }
}
