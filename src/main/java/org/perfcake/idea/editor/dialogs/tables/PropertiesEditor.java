package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.ui.table.JBTable;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.model.IProperties;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 15. 11. 2014.
 */
public class PropertiesEditor {
    private final IProperties mockCopy;
    List<Property> selectedProperties = new ArrayList<Property>();
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JTable propertiesTable;
    private JScrollPane scrollPane;


    public PropertiesEditor(final IProperties mockCopy) {
        this.mockCopy = mockCopy;

        addButton.setAction(new PropertyAddAction(mockCopy, propertiesTable));
        addButton.setText("Add");

        final EditAction propertyEditAction = new EditAction("Edit", selectedProperties.isEmpty() ? null : selectedProperties.get(0), propertiesTable);
        editButton.setAction(propertyEditAction);

        final DeleteAction propertyDeleteAction = new DeleteAction("Delete", selectedProperties, propertiesTable);
        deleteButton.setAction(propertyDeleteAction);
        //deleteButton.setText("Delete");

        propertiesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedProperties.clear();
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i) && i != mockCopy.getProperties().size()) {
                        selectedProperties.add(mockCopy.getProperties().get(i));
                    }
                }
                if (selectedProperties.isEmpty()) {
                    propertyEditAction.setDomElement(null);
                    propertyDeleteAction.setEnabled(false);
                } else {
                    propertyEditAction.setDomElement(selectedProperties.get(0));
                    propertyDeleteAction.setEnabled(true);
                }
            }
        });

//        propertiesTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) {
//                    editButton.doClick();
//                }
//            }
//        });

    }

    private void createUIComponents() {
        propertiesTable = new JBTable(new PropertiesTableModel());
    }

    private class PropertiesTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Property name", "Property value"};


        @Override
        public int getRowCount() {
            return mockCopy.getProperties().size() + 1;
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

            //adding row handling
            if (rowIndex == mockCopy.getProperties().size()) {
                mockCopy.addProperty();
                //show new empty row for adding
                fireTableRowsUpdated(rowIndex + 1, rowIndex + 1);
            }
            Property property = mockCopy.getProperties().get(rowIndex);
            if (columnIndex == 0) {
                property.getName().setStringValue((String) aValue);
            }
            if (columnIndex == 1) {
                property.getValue().setStringValue((String) aValue);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            //adding row handling
            if (rowIndex == mockCopy.getProperties().size()) {
                return null;
            }

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

        }
    }
}
