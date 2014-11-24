package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.Mode;
import org.perfcake.idea.editor.dialogs.ReporterDialog;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21. 11. 2014.
 */
public class ReporterEditor {
    private Reporting mockCopy;
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JTable reporterTable;

    public ReporterEditor(final Reporting mockCopy) {
        this.mockCopy = mockCopy;

        addButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            final Reporter newReporter = mockCopy.addReporter();
                                            final ReporterDialog editDialog = new ReporterDialog(rootPanel,
                                                    newReporter, Mode.ADD);
                                            boolean ok = editDialog.showAndGet();
                                            if (ok) {
                                                new WriteCommandAction(
                                                        newReporter.getModule().getProject(),
                                                        "Add Reporter", newReporter.getXmlElement()
                                                        .getContainingFile()) {
                                                    @Override
                                                    protected void run(@NotNull Result result)
                                                            throws Throwable {
                                                        newReporter.copyFrom(editDialog.getMockCopy());
                                                    }
                                                }.execute();
                                            } else {
                                                newReporter.undefine();
                                            }
                                            ((AbstractTableModel) reporterTable.getModel())
                                                    .fireTableDataChanged();
                                        }
                                    }

        );

        editButton.addActionListener(new

                                             ActionListener() {
                                                 @Override
                                                 public void actionPerformed(ActionEvent e) {
                                                     int selectedRow = reporterTable.getSelectedRow();
                                                     if (selectedRow > -1) {

                                                         final Reporter reporter = mockCopy.getReporters().get(
                                                                 selectedRow);

                                                         final Reporter mockCopy = (Reporter) new WriteAction() {
                                                             @Override
                                                             protected void run(@NotNull Result result)
                                                                     throws Throwable {
                                                                 result.setResult(reporter.createMockCopy(false));
                                                             }
                                                         }.execute().getResultObject();
                                                         final ReporterDialog editDialog = new ReporterDialog(
                                                                 rootPanel, mockCopy, Mode.EDIT);
                                                         boolean ok = editDialog.showAndGet();
                                                         if (ok) {
                                                             new WriteCommandAction(mockCopy.getModule()
                                                                     .getProject(), "Edit Reporter", mockCopy
                                                                     .getXmlElement().getContainingFile()) {
                                                                 @Override
                                                                 protected void run(@NotNull Result result)
                                                                         throws Throwable {
                                                                     reporter.copyFrom(editDialog.getMockCopy());
                                                                 }
                                                             }.execute();
                                                             ((AbstractTableModel) reporterTable.getModel())
                                                                     .fireTableDataChanged();
                                                         }

                                                     }
                                                 }
                                             }

        );

        deleteButton.addActionListener(new

                                               ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       int selectedRow = reporterTable.getSelectedRow();
                                                       if (selectedRow > -1) {
                                                           final Reporter reporter = mockCopy.getReporters().get(
                                                                   selectedRow);
                                                           new WriteCommandAction(reporter.getModule().getProject(),
                                                                   "Delete Reporter", reporter.getXmlElement()
                                                                   .getContainingFile()) {
                                                               @Override
                                                               protected void run(@NotNull Result result)
                                                                       throws Throwable {
                                                                   reporter.undefine();
                                                               }
                                                           }.execute();
                                                           ((AbstractTableModel) reporterTable.getModel())
                                                                   .fireTableDataChanged();
                                                       }

                                                   }
                                               }

        );

        reporterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
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
