package org.perfcake.idea.editor.dragndrop;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;

/**
 * Created by miron on 7. 12. 2014.
 */
public class ToolbarTransferHandler extends TransferHandler implements DragGestureListener {

    private String prefix;

    public ToolbarTransferHandler(String prefix) {
        this.prefix = prefix;
    }

    protected Transferable createTransferable(JComponent c) {
        JList list = (JList) c;
        String value = list.getSelectedValue().toString();
        return new StringSelection(prefix + '~' + value);
    }

    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {

    }
}
