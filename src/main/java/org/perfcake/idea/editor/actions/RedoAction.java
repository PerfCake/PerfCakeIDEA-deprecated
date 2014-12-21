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
public class RedoAction extends AbstractAction {

    UndoManager undoManager;
    FileEditorManager editorManager;
    VirtualFile fileToRedo;


    public RedoAction(DomElement domElement) {
        super("Redo", AllIcons.Actions.Redo);

        if (domElement.isValid()) {
            Project project = domElement.getModule().getProject();
            undoManager = UndoManager.getInstance(project);
            //get parent to be sure that underlying xml element exists, e.g. for empty properties there is no underlying xml tag
            XmlElement xmlElement = domElement instanceof Scenario ? domElement.getXmlElement() : domElement.getParent().getXmlElement();

            fileToRedo = xmlElement.getContainingFile().getVirtualFile();
            editorManager = FileEditorManager.getInstance(project);
            FileEditor selectedEditor = editorManager.getSelectedEditor(fileToRedo);
            boolean redoAvailable = undoManager.isRedoAvailable(selectedEditor);
            if (redoAvailable) {
                putValue(Action.NAME, undoManager.getRedoActionNameAndDescription(selectedEditor).second);
            }
            setEnabled(redoAvailable);
            return;
        }
        setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileEditor selectedEditor = editorManager.getSelectedEditor(fileToRedo);
        if (undoManager.isRedoAvailable(selectedEditor)) undoManager.redo(selectedEditor);
    }
}
