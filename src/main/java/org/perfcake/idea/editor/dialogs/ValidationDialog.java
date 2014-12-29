package org.perfcake.idea.editor.dialogs;

import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.ValidatorAddAction;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by miron on 16. 11. 2014.
 */
public class ValidationDialog extends MyDialogWrapper {
    java.util.List<Validator> selectedValidators = new ArrayList<Validator>();
    private JPanel rootPanel;
    private JCheckBox enabledCheckBox;
    private JCheckBox fastForwardCheckBox;
    private JTable validatorTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private Validation mockCopy;
    private boolean selectMode;


    public ValidationDialog(Validation mockCopy, final boolean selectMode) {
        super(true);
        this.mockCopy = mockCopy;
        this.selectMode = selectMode;
        load();
    }

    public ValidationDialog(@NotNull Component parent, Validation mockCopy) {
        this(parent, mockCopy, false);
    }

    public ValidationDialog(Component parent, final Validation mockCopy, final boolean selectMode) {
        super(parent, true);
        this.mockCopy = mockCopy;
        this.selectMode = selectMode;
        load();

    }

    private void load() {

        init();
        setTitle(selectMode ? "Attach Validator" : "Edit Validation");

        if (mockCopy.getEnabled().getValue() != null) {
            enabledCheckBox.setSelected(mockCopy.getEnabled().getValue());
        } else {
            enabledCheckBox.setSelected(false);
        }
        if (mockCopy.getFastForward().getValue() != null) {
            fastForwardCheckBox.setSelected(mockCopy.getFastForward().getValue());
        } else {
            fastForwardCheckBox.setSelected(false);
        }


        ValidatorAddAction addAction = new ValidatorAddAction(mockCopy, validatorTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final EditAction editAction = new EditAction("Edit", selectedValidators.isEmpty() ? null : selectedValidators.get(0), validatorTable);
        editButton.setAction(editAction);

        final DeleteAction deleteAction = new DeleteAction("Delete", selectedValidators, validatorTable);
        deleteButton.setAction(deleteAction);

        validatorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });

        if (selectMode) setOKActionEnabled(false);
        validatorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedValidators.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedValidators.add(mockCopy.getValidators().get(i));
                    }
                }
                if (selectedValidators.isEmpty()) {
                    deleteAction.setEnabled(false);
                    if (selectMode) setOKActionEnabled(false);
                } else {
                    deleteAction.setEnabled(true);
                    editAction.setDomElement(selectedValidators.get(0));
                    if (selectMode) setOKActionEnabled(true);
                }
            }
        });

    }

    private void createUIComponents() {
        validatorTable = new JBTable(new ValidationTableModel());

    }

    public String getSelectedValidator() {
        return selectedValidators.get(0).getId().getStringValue();
    }


    @Nullable
    @Override
    public JComponent createCenterPanel() {
        return rootPanel;
    }

    @Override
    public Validation getMockCopy() {
        if (mockCopy.getEnabled().exists() || enabledCheckBox.isSelected())
            mockCopy.getEnabled().setValue(enabledCheckBox.isSelected());
        if (mockCopy.getFastForward().exists() || fastForwardCheckBox.isSelected())
            mockCopy.getFastForward().setValue(fastForwardCheckBox.isSelected());
        return mockCopy;
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
