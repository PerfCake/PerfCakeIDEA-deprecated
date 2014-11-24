package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.components.MessagesValidationPair;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.ValidatorRef;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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


    public MessagesDialog(Component parent, final MessagesValidationPair mockPair) {
        super(parent, true);
        this.mockPair = mockPair;
        init();
        setTitle("Edit Messages");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final Message newMessage = mockPair.getMessages().addMessage();
                MessageValidationPair newMockPair = new MessageValidationPair(newMessage, mockPair.getValidation());
                final MessageDialog editDialog = new MessageDialog(rootPanel, newMockPair);
                boolean ok = editDialog.showAndGet();
                if (ok) {
                    new WriteCommandAction(newMessage.getModule().getProject(), "Add Message", newMessage.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            newMessage.copyFrom(editDialog.getMockPair().getMessage());
                        }
                    }.execute();
                    ((AbstractTableModel) messagesTable.getModel()).fireTableDataChanged();
                } else {
                    newMessage.undefine();
                }


            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int selectedRow = messagesTable.getSelectedRow();
                if (selectedRow > -1) {

                    final MessageValidationPair editMockPair = (MessageValidationPair) new WriteAction() {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            MessageValidationPair mock = new MessageValidationPair(
                                    (Message) mockPair.getMessages().getMessages().get(selectedRow).createMockCopy(false),
                                    (Validation) mockPair.getValidation().createMockCopy(false));
                            result.setResult(mock);
                        }
                    }.execute().getResultObject();
                    final MessageDialog editDialog = new MessageDialog(rootPanel, editMockPair);
                    boolean ok = editDialog.showAndGet();
                    if (ok) {
                        new WriteCommandAction(editMockPair.getMessage().getModule().getProject(), "Edit Message", editMockPair.getMessage().getXmlElement().getContainingFile()) {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                mockPair.getMessages().getMessages().get(selectedRow).copyFrom(editDialog.getMockPair().getMessage());
                                mockPair.getValidation().copyFrom(editDialog.getMockPair().getValidation());
                            }
                        }.execute();
                        ((AbstractTableModel) messagesTable.getModel()).fireTableDataChanged();
                    }

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = messagesTable.getSelectedRow();
                if (selectedRow > -1) {
                    final Message message = mockPair.getMessages().getMessages().get(selectedRow);
                    new WriteCommandAction(message.getModule().getProject(), "Delete Messsage", message.getXmlElement().getContainingFile()) {
                        @Override
                        protected void run(@NotNull Result result) throws Throwable {
                            message.undefine();
                        }
                    }.execute();
                    ((AbstractTableModel) messagesTable.getModel()).fireTableDataChanged();
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
        return rootPanel;
    }

    public MessagesValidationPair getMockPair() {
        return mockPair;
    }

    private class MessagesTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Message Uri", "Multiplicity", "Attached validators"};

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
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Message message = mockPair.getMessages().getMessages().get(rowIndex);
                if (message.isValid()) {
                    if (columnIndex == 0) {
                        return message.getUri().getStringValue();
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
