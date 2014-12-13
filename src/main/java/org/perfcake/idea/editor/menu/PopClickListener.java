package org.perfcake.idea.editor.menu;

import com.intellij.util.xml.DomElement;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 24. 11. 2014.
 */
public class PopClickListener extends MouseAdapter{

    private DomElement domElement;
    private JComponent listeningComponent;

    public PopClickListener(DomElement domElement, JComponent listeningComponent) {
        this.domElement = domElement;
        this.listeningComponent = listeningComponent;
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
        PerfCakeIdeaContextMenu menu = new PerfCakeIdeaContextMenu(domElement, listeningComponent);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
