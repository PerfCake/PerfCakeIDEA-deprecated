package org.perfcake.idea.editor.menu;

import com.intellij.util.xml.DomElement;
import org.perfcake.idea.editor.actions.RedoAction;
import org.perfcake.idea.editor.actions.UndoAction;

import javax.swing.*;

/**
 * Created by miron on 24. 11. 2014.
 */
public class PerfCakeIdeaContextMenu extends JPopupMenu{

    DomElement domElement;
    JComponent listeningComponent;

    public PerfCakeIdeaContextMenu(DomElement domElement, JComponent listeningComponent) {
        this.domElement = domElement;
        this.listeningComponent = listeningComponent;

//        AnAction undoAction = ActionManager.getInstance().getAction(IdeActions.ACTION_UNDO);
//        AnAction redoAction = ActionManager.getInstance().getAction(IdeActions.ACTION_REDO);
//        ArrayList<AnAction> actions = new ArrayList<>();
//        actions.add(undoAction);
//        actions.add(redoAction);
//        DefaultActionGroup defaultActionGroup = new DefaultActionGroup("UndoRedo", actions);
//        ActionPopupMenu actionPopupMenu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.EDITOR_POPUP, defaultActionGroup);
//        JPopupMenu popupMenu = actionPopupMenu.getComponent();
//        Component[] components = popupMenu.getComponents();
//
//        for(Component c : components){
//            add((JMenuItem) c);
//        }


        JMenuItem undoItem = new JMenuItem();
        undoItem.setAction(new UndoAction(domElement));
        add(undoItem);

//        JMenuItem undoItem2 = new JMenuItem();
//        undoItem2.setAction(new UndoAction2());
//        add(undoItem2);

        JMenuItem redoItem = new JMenuItem();
        redoItem.setAction(new RedoAction(domElement));
        add(redoItem);

        add(new JSeparator());

        ActionMap actionMap = listeningComponent.getActionMap();

        Action addAction = actionMap.get(ActionType.ADD);
        if (addAction != null) {
            JMenuItem addItem = new JMenuItem();
            addItem.setAction(addAction);
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
            add(editItem);
        }

        Action deleteAction = actionMap.get(ActionType.DELETE);
        if (deleteAction != null) {
            JMenuItem deleteItem = new JMenuItem();
            deleteItem.setAction(deleteAction);
            add(deleteItem);
        }


//        if(domElement instanceof Properties){
//            JMenuItem addPropertyItem = new JMenuItem();
//            addPropertyItem.setAction(new PropertyAddAction((IProperties) domElement, listeningComponent));
//            add(addPropertyItem);
//
//            JMenuItem editPropertiesItem = new JMenuItem();
//            editPropertiesItem.setAction(new PropertiesEditAction((IProperties) domElement, listeningComponent));
//            add(editPropertiesItem);
//        }
//
//        if(domElement instanceof Property){
//            JMenuItem deletePropertyItem = new JMenuItem();
//
//            List<Property> propertyList = new ArrayList<>();
//            propertyList.add((Property) domElement);
//            deletePropertyItem.setAction(new PropertyDeleteAction(propertyList, listeningComponent));
//            add(deletePropertyItem);
//
//            JMenuItem editPropertyItem = new JMenuItem();
//            editPropertyItem.setAction(new PropertyEditAction((Property) domElement, listeningComponent));
//            add(editPropertyItem);
//        }

    }

//    public JPopupMenu createMenu(){
//        AnAction undoAction = ActionManager.getInstance().getAction(IdeActions.ACTION_UNDO);
//        AnAction redoAction = ActionManager.getInstance().getAction(IdeActions.ACTION_REDO);
//        ArrayList<AnAction> actions = new ArrayList<>();
//        actions.add(undoAction);
//        actions.add(redoAction);
//        DefaultActionGroup defaultActionGroup = new DefaultActionGroup("UndoRedo", actions);
//        ActionPopupMenu actionPopupMenu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.EDITOR_POPUP, defaultActionGroup);
//        JPopupMenu popupMenu = actionPopupMenu.getComponent();
//
//
//        JMenuItem undoItem = new JMenuItem();
//        undoItem.setAction(new UndoAction(domElement));
//        popupMenu.add(undoItem);
//
//        ActionMap actionMap = listeningComponent.getActionMap();
//
//        JMenuItem addItem = new JMenuItem();
//        addItem.setAction(actionMap.get(ActionType.ADD));
//        popupMenu.add(addItem);
//
//        return popupMenu;
//    }


}