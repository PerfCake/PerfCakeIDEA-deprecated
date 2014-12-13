package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.HeaderAddAction;
import org.perfcake.idea.model.Header;
import org.perfcake.idea.model.Message;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 18. 11. 2014.
 */
public class HeaderEditor {
    List<Header> selectedHeaders = new ArrayList<Header>();
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton addButton;
    private JButton editButton;
    private JTable headerTable;
    private Message mockCopy;

    public HeaderEditor(final Message mockCopy) {
        this.mockCopy = mockCopy;

        HeaderAddAction addAction = new HeaderAddAction(mockCopy, headerTable);
        addButton.setAction(addAction);
        addButton.setText("Add");

        final EditAction editAction = new EditAction("Edit Header", selectedHeaders.isEmpty() ? null : selectedHeaders.get(0), headerTable);
        editButton.setAction(editAction);
        editButton.setText("Edit");

        final DeleteAction deleteAction = new DeleteAction("Delete Header", selectedHeaders, headerTable);
        deleteButton.setAction(deleteAction);
        deleteButton.setText("Delete");

        headerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedHeaders.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for(int i = minIndex; i <= maxIndex; i++){
                    if(lsm.isSelectedIndex(i)){
                        selectedHeaders.add(mockCopy.getHeaders().get(i));
                    }
                }
                if(selectedHeaders.isEmpty()){
                    deleteAction.setEnabled(false);
                }else{
                    deleteAction.setEnabled(true);
                    editAction.setDomElement(selectedHeaders.get(0));
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
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Header header = mockCopy.getHeaders().get(rowIndex);
            if(columnIndex == 0){
                header.getName().setStringValue((String) aValue);
            }
            if(columnIndex == 1){
                header.getValue().setStringValue((String) aValue);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

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
        }
    }
}
