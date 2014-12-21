package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.MessageAddAction;
import org.perfcake.idea.editor.actions.MessageEditAction;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.ValidatorRef;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by miron on 19. 11. 2014.
 */
public class MessagesDialog extends DialogWrapper {
    private MessagesValidationPair mockPair;
    private JPanel rootPanel;
    private JTable messagesTable;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;

    private java.util.List<Message> selectedMessages = new ArrayList<>();

    public MessagesDialog(MessagesValidationPair mockPair) {
        super(true);
        this.mockPair = mockPair;
        init();
        load();
    }

    public MessagesDialog(Component parent, final MessagesValidationPair mockPair) {
        super(parent, true);
        this.mockPair = mockPair;
        init();
        load();
    }

    private void load() {
        setTitle("Edit Messages");

        MessageAddAction addAction = new MessageAddAction(mockPair, messagesTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final MessageEditAction editAction = new MessageEditAction(selectedMessages.isEmpty() ? null : new MessageValidationPair(selectedMessages.get(0), mockPair.getValidation()), messagesTable);
        editButton.setAction(editAction);
        editButton.setText("Edit");

        final DeleteAction deleteAction = new DeleteAction("Delete Messages", selectedMessages, messagesTable);
        deleteButton.setAction(deleteAction);
        deleteButton.setText("Delete");

        messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMessages.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        selectedMessages.add(mockPair.getMessages().getMessages().get(i));
                    }
                }
                if (selectedMessages.isEmpty()) {
                    deleteAction.setEnabled(false);
                    editAction.setMessageValidationPair(null);
                } else {
                    deleteAction.setEnabled(true);
                    editAction.setMessageValidationPair(new MessageValidationPair(selectedMessages.get(0), mockPair.getValidation()));
                }
            }
        });

        messagesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });
    }

    private void createUIComponents() {
        messagesTable = new JBTable(new MessagesTableModel());
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return getRootPanel();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public MessagesValidationPair getMockPair() {
        return mockPair;
    }

    private class MessagesTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Message", "Multiplicity", "Attached validators"};

        @Override
        public int getRowCount() {
            return mockPair.getMessages().getMessages().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1 ? true : false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Message message = mockPair.getMessages().getMessages().get(rowIndex);
            if (columnIndex == 1) {
                message.getMultiplicity().setStringValue((String) aValue);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Message message = mockPair.getMessages().getMessages().get(rowIndex);
                if (message.isValid()) {
                    if (columnIndex == 0) {
                        String content = message.getContent().getStringValue() == null ?
                                message.getUri().getStringValue() : message.getContent().getStringValue();
                        return content == null ? "" : (content.length() > 20 ? content.substring(0, 17) + "..." : content);
                    }
                    if (columnIndex == 1) {
                        return message.getMultiplicity().getStringValue();
                    }
                    if (columnIndex == 2) {
                        String validators = "";
                        for (ValidatorRef vRef : message.getValidatorRefs()) {
                            validators += vRef.getId().getStringValue() + ", ";
                        }
                        if (!validators.isEmpty()) validators = validators.substring(0, validators.length() - 2);
                        return validators;
                    }
                }
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
