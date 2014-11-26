package org.perfcake.idea.editor.menu;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 24. 11. 2014.
 */
public class PopClickListener extends MouseAdapter{

    private Project project;
    private FileEditor editor;

    public PopClickListener(PerfCakeIdeaContextMenu menu) {

        this.project = project;
        this.editor = editor;
    }

    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PerfCakeIdeaContextMenu menu = new PerfCakeIdeaContextMenu(project, editor);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
