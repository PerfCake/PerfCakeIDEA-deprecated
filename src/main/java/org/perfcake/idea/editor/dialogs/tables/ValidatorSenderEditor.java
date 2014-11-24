package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.dialogs.ValidationDialog;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.model.ValidatorRef;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by miron on 16. 11. 2014.
 */
public class ValidatorSenderEditor {
    private JPanel rootPanel;
    private JTable validatorTable;
    private JButton detachButton;
    private JButton attachButton;
    private MessageValidationPair mockPair;

    public ValidatorSenderEditor(final MessageValidationPair mockPair) {
        this.mockPair = mockPair;
        attachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Validation newMockCopy = (Validation) new WriteAction() {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        result.setResult(ValidatorSenderEditor.this.mockPair.getValidation().createMockCopy(false));
                    }
                }.execute().getResultObject();

                final ValidationDialog editDialog = new ValidationDialog(rootPanel, newMockCopy, true);
                boolean ok = editDialog.showAndGet();

                if (ok) {
                    new WriteCommandAction(newMockCopy.getModule().getProject(), "Edit Validation", newMockCopy.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            String selectedValidator = editDialog.getSelectedValidator();
                            mockPair.getValidation().copyFrom(newMockCopy);
                            mockPair.getMessage().addValidatorRef().getId().setStringValue(editDialog.getSelectedValidator());
                        }
                    }.execute();
                    ((AbstractTableModel) validatorTable.getModel()).fireTableDataChanged();
                }
            }
        });
        detachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = validatorTable.getSelectedRow();
                if (selectedRow != -1) {
                    final ValidatorRef validatorRef = mockPair.getMessage().getValidatorRefs().get(selectedRow);
                    new WriteCommandAction(validatorRef.getModule().getProject(), "Delete Property", validatorRef.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            validatorRef.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) validatorTable.getModel()).fireTableDataChanged();
                }
            }
        });
    }

    private void createUIComponents() {
        validatorTable = new JBTable(new ValidatorTableModel());
    }

    private class ValidatorTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Validator id", "Validator"};

        @Override
        public int getRowCount() {
            return mockPair.getMessage().getValidatorRefs().size();
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
                ValidatorRef vRef = mockPair.getMessage().getValidatorRefs().get(rowIndex);
                String validatorId = vRef.getId().getStringValue();
                List<Validator> validators = mockPair.getValidation().getValidators();
                for (Validator validator : validators) {
                    if (validatorId.equals(validator.getId().getStringValue())) {
                        if (columnIndex == 0) {
                            return validator.getId().getStringValue();
                        }
                        if (columnIndex == 1) {
                            return validator.getClazz().getStringValue();
                        }
                    }
                }
                //validator was not found
                if (columnIndex == 0) {
                    return validatorId;
                }
                if (columnIndex == 1) {
                    return "Validator with this Id does not exist";
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
