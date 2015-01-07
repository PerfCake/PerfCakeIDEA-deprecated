package org.perfcake.idea.editor.menu;

import com.intellij.util.xml.DomElement;
import org.perfcake.idea.editor.actions.RedoAction;
import org.perfcake.idea.editor.actions.UndoAction;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by miron on 24. 11. 2014.
 */
public class PerfCakeIdeaContextMenu extends JPopupMenu{

    DomElement domElement;
    JComponent listeningComponent;

    public PerfCakeIdeaContextMenu(DomElement domElement, JComponent listeningComponent) {
        this.domElement = domElement;
        this.listeningComponent = listeningComponent;


        JMenuItem undoItem = new JMenuItem();
        undoItem.setAction(new UndoAction(domElement));
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        add(undoItem);

        JMenuItem redoItem = new JMenuItem();
        redoItem.setAction(new RedoAction(domElement));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        add(redoItem);

        add(new JSeparator());

        ActionMap actionMap = listeningComponent.getActionMap();

        Action addAction = actionMap.get(ActionType.ADD);
        if (addAction != null) {
            JMenuItem addItem = new JMenuItem();
            addItem.setAction(addAction);
            addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.SHIFT_MASK));
            add(addItem);
        }

        Action addAction2 = actionMap.get(ActionType.ADD2);
        if (addAction2 != null) {
            JMenuItem addItem2 = new JMenuItem();
            addItem2.setAction(addAction2);
            add(addItem2);
        }

        Action addAction3 = actionMap.get(ActionType.ADD3);
        if (addAction3 != null) {
            JMenuItem addItem3 = new JMenuItem();
            addItem3.setAction(addAction3);
            add(addItem3);
        }

        Action editAction = actionMap.get(ActionType.EDIT);
        if (editAction != null) {
            JMenuItem editItem = new JMenuItem();
            editItem.setAction(editAction);
            editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_MASK));
            add(editItem);
        }

        Action deleteAction = actionMap.get(ActionType.DELETE);
        if (deleteAction != null) {
            JMenuItem deleteItem = new JMenuItem();
            deleteItem.setAction(deleteAction);
            deleteItem.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
            add(deleteItem);
        }

    }


}