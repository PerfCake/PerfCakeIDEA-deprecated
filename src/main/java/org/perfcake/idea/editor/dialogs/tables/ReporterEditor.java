package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.ReporterAddAction;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 21. 11. 2014.
 */
public class ReporterEditor {
    List<Reporter> selectedReporters = new ArrayList<>();
    private Reporting mockCopy;
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JTable reporterTable;

    public ReporterEditor(final Reporting mockCopy) {
        this.mockCopy = mockCopy;

        ReporterAddAction addAction = new ReporterAddAction(mockCopy, reporterTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final EditAction editAction = new EditAction<Reporter>("Edit Reporter", selectedReporters.isEmpty() ? null : selectedReporters.get(0), reporterTable);
        editButton.setAction(editAction);
        editButton.setText("Edit");

        final DeleteAction deleteAction = new DeleteAction<Reporter>("Delete Reporter", selectedReporters, reporterTable);
        deleteButton.setAction(deleteAction);
        deleteButton.setText("Delete");

        reporterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });

        reporterTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedReporters.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedReporters.add(mockCopy.getReporters().get(i));
                    }
                }
                if (selectedReporters.isEmpty()) {
                    editAction.setDomElement(null);
                    deleteAction.setEnabled(false);
                } else {
                    editAction.setDomElement(selectedReporters.get(0));
                    deleteAction.setEnabled(true);
                }
            }
        });
    }

    private void createUIComponents() {
        reporterTable = new JBTable(new ReporterTableModel());
    }


    private class ReporterTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Enabled", "Reporter"};

        @Override
        public int getRowCount() {
            return mockCopy.getReporters().size();
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
            try {
                Reporter Reporter = mockCopy.getReporters().get(rowIndex);
                if (Reporter.isValid()) {
                    if (columnIndex == 0) {
                        if (Reporter.getEnabled().getStringValue() != null
                                && Reporter.getEnabled().getStringValue()
                                .equals("true")) {
                            return '*';
                        }
                        return null;
                    }
                    if (columnIndex == 1) {
                        return Reporter.getClazz().getStringValue();
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
