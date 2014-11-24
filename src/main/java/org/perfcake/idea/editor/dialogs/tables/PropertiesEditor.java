package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.model.IProperties;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 15. 11. 2014.
 */
public class PropertiesEditor {
    private final IProperties mockCopy;
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JTable propertiesTable;
    private JScrollPane scrollPane;

    public PropertiesEditor(final IProperties mockCopy) {
        this.mockCopy = mockCopy;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Property newProperty = mockCopy.addProperty();
                final PropertyDialog editDialog = new PropertyDialog(rootPanel, newProperty);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newProperty.getModule().getProject(), "Add Property", newProperty.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newProperty.copyFrom(editDialog.getMockCopy());
                        }
                    }.execute();
                    ((AbstractTableModel) propertiesTable.getModel()).fireTableDataChanged();
                } else {
                    newProperty.undefine();
                }


            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = propertiesTable.getSelectedRow();
                if (selectedRow > -1) {

                    final Property property = mockCopy.getProperties().get(selectedRow);

                    final Property mockCopy = (Property) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            result.setResult(property.createMockCopy(false));
                        }
                    }.execute().getResultObject();
                    final PropertyDialog editDialog = new PropertyDialog(rootPanel, mockCopy);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Property", mockCopy.getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                property.copyFrom(editDialog.getMockCopy());
                            }
                        }.execute();
                        ((AbstractTableModel) propertiesTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = propertiesTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Property property = mockCopy.getProperties().get(selectedRow);
                    new WriteCommandAction(property.getModule().getProject(), "Delete Property", property.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            property.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) propertiesTable.getModel()).fireTableDataChanged();
                }

            }
        });

        propertiesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });

    }

    private void createUIComponents() {
        propertiesTable = new JBTable(new PropertiesTableModel());
    }

    private class PropertiesTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Property name", "Property value"};

        @Override
        public int getRowCount() {
            return mockCopy.getProperties().size();
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
                Property property = mockCopy.getProperties().get(rowIndex);
                if (property.isValid()) {
                    if (columnIndex == 0) {
                        return property.getName();
                    }
                    if (columnIndex == 1) {
                        return property.getValue();
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
