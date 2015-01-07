package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.DestinationAddAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 20. 11. 2014.
 */
public class DestinationEditor {
    List<Destination> selectedDestinations = new ArrayList<Destination>();
    private Reporter mockCopy;
    private JPanel rootPanel;
    private JTable destinationTable;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;

    public DestinationEditor(final Reporter mockCopy) {
        this.mockCopy = mockCopy;

        DestinationAddAction addAction = new DestinationAddAction(mockCopy, destinationTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final EditAction editAction = new EditAction<Destination>("Edit Destination", null, destinationTable);
        editButton.setAction(editAction);
        editButton.setText("Edit");

        final DeleteAction deleteAction = new DeleteAction<Destination>("Delete Destination", selectedDestinations, destinationTable);
        deleteButton.setAction(deleteAction);
        deleteButton.setText("Delete");

        destinationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedDestinations.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedDestinations.add(mockCopy.getDestinations().get(i));
                    }
                }
                if (selectedDestinations.isEmpty()) {
                    editAction.setDomElement(null);
                    deleteAction.setEnabled(false);
                } else {
                    editAction.setDomElement(selectedDestinations.get(0));
                    deleteAction.setEnabled(true);
                }
            }
        });

        destinationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });
    }

    private void createUIComponents() {
        destinationTable = new JBTable(new DestinationTableModel());
    }

    private class DestinationTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Destination", "Enabled"};

        @Override
        public int getRowCount() {
            return mockCopy.getDestinations().size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }


        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Destination destination = mockCopy.getDestinations().get(rowIndex);
                if (destination.isValid()) {
                    if (columnIndex == 0) {
                        return destination.getClazz().getStringValue();
                    }

                    if (columnIndex == 1) {
                        if(destination.getEnabled().getStringValue() != null && destination.getEnabled().getStringValue().equals("true")){
                            return '*';
                        }
                        return null;
                    }
                }
                return null;

        }
    }
}
