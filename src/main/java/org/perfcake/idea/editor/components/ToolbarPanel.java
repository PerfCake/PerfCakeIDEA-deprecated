package org.perfcake.idea.editor.components;

import com.intellij.ui.components.JBList;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.VerticalLayout;
import org.perfcake.idea.editor.dragndrop.ToolbarTransferHandler;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by miron on 22.10.2014.
 */
public class ToolbarPanel extends JPanel {

    java.util.List<ListSelectionModel> jlistModels = new ArrayList();

    public ToolbarPanel() {
        setLayout(new VerticalLayout());
        //setBackground(Color.WHITE);


        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        try {

            addDragGroup("Messages", new String[]{"Message"});

            String[] validators = classProvider.findValidators();
            addDragGroup("Validators", validators);

            String[] reporters = classProvider.findReporters();
            addDragGroup("Reporters", reporters);

            String[] destinations = classProvider.findDestinations();
            addDragGroup("Destinations", destinations);

            addDragGroup("Properties", new String[]{"Property"});

            //addDragGroup("Connections", new String[]{"Attach Validator"});

        } catch (PerfCakeClassProviderException e) {
            throw new RuntimeException("Error initializing toolbar", e);
        }
    }

    private void addDragGroup(String groupName, String[] items) {
        //create collapsible pane with animated hiding
        JXCollapsiblePane pane = new JXCollapsiblePane(new BorderLayout());
        pane.setAnimated(true);

        //add items to pane
        JBList list = new JBList(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.getSelectionModel().addListSelectionListener(new MyListSelectionListener());
        list.setDragEnabled(true);

        list.setTransferHandler(new ToolbarTransferHandler(groupName));
        pane.add(list);
        jlistModels.add(list.getSelectionModel());

        //get toggling action and set it to JMenuItem with groupName
        Action toggleAction = pane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
        toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON,
                UIManager.getIcon("Tree.expandedIcon"));
        toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON,
                UIManager.getIcon("Tree.collapsedIcon"));
        JMenuItem group = new JMenuItem(toggleAction);
        group.setBorder(BorderFactory.createEtchedBorder());
        group.setText(groupName);

        //adds group and pane with items to this toolbar
        add(group);
        add(pane);
    }

    private void deselectOthers(ListSelectionModel lsm) {
        for (ListSelectionModel model : jlistModels) {
            if (!(lsm == model)) {
                model.clearSelection();
            }
        }
    }

    class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            int minIndex = lsm.getMinSelectionIndex();
            if (minIndex != -1) {
                deselectOthers(lsm);
            }
        }
    }

    class DragListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            JComponent jlist = (JComponent) e.getSource();
            TransferHandler transferHandler = jlist.getTransferHandler();
            System.out.println("Export as drag on jList called from listener.");
            transferHandler.exportAsDrag(jlist, new MouseEvent(jlist, MouseEvent.MOUSE_DRAGGED, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()), TransferHandler.COPY);
        }
    }
}
