package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.HeaderDialog;
import org.perfcake.idea.model.Header;
import org.perfcake.idea.model.Message;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 18. 11. 2014.
 */
public class HeaderEditor {
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton addButton;
    private JButton editButton;
    private JTable headerTable;
    private Message mockCopy;

    public HeaderEditor(final Message mockCopy) {
        this.mockCopy = mockCopy;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Header newHeader = mockCopy.addHeader();
                final HeaderDialog editDialog = new HeaderDialog(rootPanel, newHeader);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newHeader.getModule().getProject(), "Add Header", newHeader.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newHeader.copyFrom(editDialog.getMockCopy());
                        }
                    }.execute();
                    ((AbstractTableModel) headerTable.getModel()).fireTableDataChanged();
                } else {
                    newHeader.undefine();
                }


            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = headerTable.getSelectedRow();
                if (selectedRow > -1) {

                    final Header Header = mockCopy.getHeaders().get(selectedRow);

                    final Header mockCopy = (Header) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            result.setResult(Header.createMockCopy(false));
                        }
                    }.execute().getResultObject();
                    final HeaderDialog editDialog = new HeaderDialog(rootPanel, mockCopy);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Header", mockCopy.getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                Header.copyFrom(editDialog.getMockCopy());
                            }
                        }.execute();
                        ((AbstractTableModel) headerTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = headerTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Header Header = mockCopy.getHeaders().get(selectedRow);
                    new WriteCommandAction(Header.getModule().getProject(), "Delete Header", Header.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            Header.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) headerTable.getModel()).fireTableDataChanged();
                }

            }
        });

        headerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });
    }

    private void createUIComponents() {
        headerTable = new JBTable(new HeaderTableModel());
    }

    private class HeaderTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Header name", "Header value"};

        @Override
        public int getRowCount() {
            return mockCopy.getHeaders().size();
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
                Header header = mockCopy.getHeaders().get(rowIndex);
                if (header.isValid()) {
                    if (columnIndex == 0) {
                        return header.getName();
                    }
                    if (columnIndex == 1) {
                        return header.getValue();
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
