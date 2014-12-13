package org.perfcake.idea.editor.dialogs.tables;

import com.intellij.util.xml.DomElement;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

/**
 * Created by miron on 10. 12. 2014.
 */
public class TableSelectionListener implements ListSelectionListener {

    private final List<DomElement> selectedList;
    private final EditAction editAction;
    private final DeleteAction deleteAction;
    private List<DomElement> sourceList;

    public TableSelectionListener(List<DomElement> selectedList, List<DomElement> sourceList, EditAction editAction, DeleteAction deleteAction) {
        this.selectedList = selectedList;
        this.sourceList = sourceList;
        this.editAction = editAction;
        this.deleteAction = deleteAction;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedList.clear();
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
                selectedList.add(sourceList.get(i));
            }
        }
        if (selectedList.isEmpty()) {
            editAction.setDomElement(null);
            deleteAction.setEnabled(false);
        } else {
            editAction.setDomElement(selectedList.get(0));
            deleteAction.setEnabled(true);
        }
    }
}
