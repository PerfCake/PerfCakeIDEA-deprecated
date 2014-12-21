package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.command.undo.UndoManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.xml.DomElement;
import org.perfcake.idea.model.Scenario;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 29. 11. 2014.
 */
public class UndoAction extends AbstractAction {

    UndoManager undoManager;
    FileEditorManager editorManager;
    VirtualFile fileToUndo;


    public UndoAction(DomElement domElement) {
        super("Undo", AllIcons.Actions.Undo);


        if (domElement.isValid()) {
            Project project = domElement.getModule().getProject();
            undoManager = UndoManager.getInstance(project);
            //get parent to be sure that underlying xml element exists, e.g. for empty properties there is no underlying xml tag
            XmlElement xmlElement = domElement instanceof Scenario ? domElement.getXmlElement() : domElement.getParent().getXmlElement();

            fileToUndo = xmlElement.getContainingFile().getVirtualFile();
            editorManager = FileEditorManager.getInstance(project);
            FileEditor selectedEditor = editorManager.getSelectedEditor(fileToUndo);
            boolean undoAvailable = undoManager.isUndoAvailable(selectedEditor);
            if (undoAvailable) {
                putValue(Action.NAME, undoManager.getUndoActionNameAndDescription(selectedEditor).second);
            }
            setEnabled(undoAvailable);
            return;
        }
        setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileEditor selectedEditor = editorManager.getSelectedEditor(fileToUndo);
        if (undoManager.isUndoAvailable(selectedEditor)) undoManager.undo(selectedEditor);
    }

}
