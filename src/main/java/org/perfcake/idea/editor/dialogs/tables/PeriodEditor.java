package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PeriodAddAction;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Period;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 19. 11. 2014.
 */
public class PeriodEditor {
    private final Destination mockCopy;
    private JPanel rootPanel;
    private JTable periodTable;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;

    private List<Period> selectedPeriods = new ArrayList<>();

    public PeriodEditor(final Destination mockCopy) {
        this.mockCopy = mockCopy;

        PeriodAddAction addAction = new PeriodAddAction(mockCopy, periodTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final EditAction editAction = new EditAction("Edit Period", null, periodTable);
        editButton.setAction(editAction);
        editButton.setText("Edit");

        final DeleteAction deleteAction = new DeleteAction("Delete Period", selectedPeriods, periodTable);
        deleteButton.setAction(deleteAction);
        deleteButton.setText("Delete");

        periodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });

        periodTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedPeriods.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedPeriods.add(mockCopy.getPeriods().get(i));
                    }
                }
                if (selectedPeriods.isEmpty()) {
                    editAction.setDomElement(null);
                    deleteAction.setEnabled(false);
                } else {
                    editAction.setDomElement(selectedPeriods.get(0));
                    deleteAction.setEnabled(true);
                }
            }
        });
    }

    private void createUIComponents() {
        periodTable = new JBTable(new PeriodTableModel());
    }

    private class PeriodTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Period", "Period value"};

        @Override
        public int getRowCount() {
            return mockCopy.getPeriods().size();
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
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Period period = mockCopy.getPeriods().get(rowIndex);
            if (columnIndex == 0) {
                period.getType().setStringValue((String) aValue);
            }
            if (columnIndex == 1) {
                period.getValue().setStringValue((String) aValue);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Period period = mockCopy.getPeriods().get(rowIndex);
                if (period.isValid()) {
                    if (columnIndex == 0) {
                        return period.getType().getStringValue();
                    }
                    if (columnIndex == 1) {
                        return period.getValue().getStringValue();
                    }
                }
                return null;

        }
    }
}
