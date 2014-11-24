package org.perfcake.idea.editor.menu;

import com.intellij.openapi.command.undo.UndoManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 24. 11. 2014.
 */
public class PerfCakeIdeaContextMenu extends JPopupMenu{

    public PerfCakeIdeaContextMenu(final Project project, final FileEditor editor) {
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("UNDO");
                UndoManager instance = UndoManager.getInstance(project);
                if (instance.isUndoAvailable(editor)) instance.undo(editor);
            }
        });
        add(undo);
    }

}