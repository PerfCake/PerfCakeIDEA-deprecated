package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.DestinationDialog;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Reporter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 20. 11. 2014.
 */
public class DestinationEditor {
    private Reporter mockCopy;
    private JPanel rootPanel;
    private JTable destinationTable;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;

    public DestinationEditor(final Reporter mockCopy) {
        this.mockCopy = mockCopy;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Destination newDestination = mockCopy.addDestination();
                final DestinationDialog editDialog = new DestinationDialog(rootPanel, newDestination, Mode.ADD);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newDestination.getModule().getProject(), "Add Destination", newDestination.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newDestination.copyFrom(editDialog.getMockCopy());
                        }
                    }.execute();
                } else {
                    newDestination.undefine();
                }
                ((AbstractTableModel) destinationTable.getModel()).fireTableDataChanged();

            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = destinationTable.getSelectedRow();
                if (selectedRow > -1) {

                    final Destination Destination = mockCopy.getDestinations().get(selectedRow);

                    final Destination mockCopy = (Destination) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            result.setResult(Destination.createMockCopy(false));
                        }
                    }.execute().getResultObject();
                    final DestinationDialog editDialog = new DestinationDialog(rootPanel, mockCopy, Mode.EDIT);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Destination", mockCopy.getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                Destination.copyFrom(editDialog.getMockCopy());
                            }
                        }.execute();
                        ((AbstractTableModel) destinationTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = destinationTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Destination Destination = mockCopy.getDestinations().get(selectedRow);
                    new WriteCommandAction(Destination.getModule().getProject(), "Delete Destination", Destination.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            Destination.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) destinationTable.getModel()).fireTableDataChanged();
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
        private final String[] columnNames = {"Enabled", "Destination"};

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
            try {
                Destination destination = mockCopy.getDestinations().get(rowIndex);
                if (destination.isValid()) {
                    if (columnIndex == 0) {
                        if(destination.getEnabled().getStringValue() != null && destination.getEnabled().getStringValue().equals("true")){
                            return '*';
                        }
                        return null;
                    }
                    if (columnIndex == 1) {
                        return destination.getClazz().getStringValue();
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
