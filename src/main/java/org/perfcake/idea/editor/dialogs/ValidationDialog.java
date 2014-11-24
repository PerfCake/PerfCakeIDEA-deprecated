package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 16. 11. 2014.
 */
public class ValidationDialog extends DialogWrapper {
    private JPanel rootPanel;
    private JCheckBox enabledCheckBox;
    private JCheckBox fastForwardCheckBox;
    private JTable validatorTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private Validation mockCopy;
    private boolean selectMode;
    @Nullable
    private String selectedValidatorId = null;

    public ValidationDialog(@NotNull Component parent, Validation mockCopy) {
        this(parent, mockCopy, false);
    }

    public ValidationDialog(Component parent, final Validation mockCopy, boolean selectMode) {
        super(parent, true);
        this.mockCopy = mockCopy;
        this.selectMode = selectMode;
        init();
        setTitle("Edit Validation");

        enabledCheckBox.setSelected(mockCopy.getEnabled().getValue());
        fastForwardCheckBox.setSelected(mockCopy.getFastForward().getValue());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Validator newValidator = mockCopy.addValidator();
                final ValidatorDialog editDialog = new ValidatorDialog(rootPanel, newValidator);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newValidator.getModule().getProject(), "Add Validator", newValidator.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newValidator.copyFrom(editDialog.getMockCopy());
                        }
                    }.execute();
                    ((AbstractTableModel) validatorTable.getModel()).fireTableDataChanged();
                } else {
                    newValidator.undefine();
                }


            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = validatorTable.getSelectedRow();
                if (selectedRow > -1) {

                    final Validator validator = mockCopy.getValidators().get(selectedRow);

                    final Validator mockCopy = (Validator) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            result.setResult(validator.createMockCopy(false));
                        }
                    }.execute().getResultObject();
                    final ValidatorDialog editDialog = new ValidatorDialog(rootPanel, mockCopy);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(mockCopy.getModule().getProject(), "Edit Validator", mockCopy.getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                validator.copyFrom(editDialog.getMockCopy());
                            }
                        }.execute();
                        ((AbstractTableModel) validatorTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = validatorTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Validator validator = mockCopy.getValidators().get(selectedRow);
                    new WriteCommandAction(validator.getModule().getProject(), "Delete Validator", validator.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            validator.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) validatorTable.getModel()).fireTableDataChanged();
                }

            }
        });

        validatorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });
    }

    private void createUIComponents() {
        validatorTable = new JBTable(new ValidationTableModel());
        if (selectMode) {
            setOKActionEnabled(false);
            validatorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int minSelectionIndex = validatorTable.getSelectionModel().getMinSelectionIndex();
                    int maxSelectionIndex = validatorTable.getSelectionModel().getMaxSelectionIndex();
                    if (minSelectionIndex == maxSelectionIndex && minSelectionIndex != -1) {
                        setOKActionEnabled(true);
                        setSelectedValidator(maxSelectionIndex);
                    } else {
                        setOKActionEnabled(false);
                    }
                }
            });
        }
    }

    public String getSelectedValidator() {
        return selectedValidatorId;
    }

    private void setSelectedValidator(int selectedRow) {
        Validator validator = mockCopy.getValidators().get(selectedRow);
        selectedValidatorId = validator.getId().getStringValue();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    private class ValidationTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Validator Id", "Validator"};

        @Override
        public int getRowCount() {
            return mockCopy.getValidators().size();
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
                Validator validator = mockCopy.getValidators().get(rowIndex);
                if (validator.isValid()) {
                    if (columnIndex == 0) {
                        return validator.getId().getStringValue();
                    }
                    if (columnIndex == 1) {
                        return validator.getClazz().getStringValue();
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
