package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.ValidatorAttachAction;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.model.ValidatorRef;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 16. 11. 2014.
 */
public class ValidatorMessageEditor {
    List<ValidatorRef> selectedValidatorRefs = new ArrayList<>();
    private JPanel rootPanel;
    private JTable validatorTable;
    private JButton detachButton;
    private JButton attachButton;
    private MessageValidationPair mockPair;

    public ValidatorMessageEditor(final MessageValidationPair mockPair) {
        this.mockPair = mockPair;

        ValidatorAttachAction validatorAttachAction = new ValidatorAttachAction(mockPair, validatorTable);
        attachButton.setAction(validatorAttachAction);
        attachButton.setText("Attach");

        final DeleteAction detachAction = new DeleteAction("Detach Validator", selectedValidatorRefs, validatorTable);
        detachButton.setAction(detachAction);
        detachButton.setText("Detach");

        validatorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedValidatorRefs.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedValidatorRefs.add(mockPair.getMessage().getValidatorRefs().get(i));
                    }
                }
                if (selectedValidatorRefs.isEmpty()) {
                    detachAction.setEnabled(false);
                } else {
                    detachAction.setEnabled(true);
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
