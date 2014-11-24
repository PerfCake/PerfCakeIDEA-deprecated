package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.PeriodDialog;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Period;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public PeriodEditor(final Destination mockCopy) {
        this.mockCopy = mockCopy;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Period newPeriod = mockCopy.addPeriod();
                final PeriodDialog editDialog = new PeriodDialog(rootPanel, newPeriod);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newPeriod.getModule().getProject(), "Add Period", newPeriod.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newPeriod.copyFrom(editDialog.getMockCopy());
                        }
                    }.execute();
                    ((AbstractTableModel) periodTable.getModel()).fireTableDataChanged();
                } else {
                    newPeriod.undefine();
                }


            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = periodTable.getSelectedRow();
                if (selectedRow > -1) {

                    final Period Period = mockCopy.getPeriods().get(selectedRow);

                    final Period mockCopy = (Period) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            result.setResult(Period.createMockCopy(false));
                        }
                    }.execute().getResultObject();
                    final PeriodDialog editDialog = new PeriodDialog(rootPanel, mockCopy);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Period", mockCopy.getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                Period.copyFrom(editDialog.getMockCopy());
                            }
                        }.execute();
                        ((AbstractTableModel) periodTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = periodTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Period Period = mockCopy.getPeriods().get(selectedRow);
                    new WriteCommandAction(Period.getModule().getProject(), "Delete Period", Period.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            Period.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) periodTable.getModel()).fireTableDataChanged();
                }

            }
        });

        periodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
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
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
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
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
