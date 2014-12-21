package org.perfcake.idea.editor.dragndrop;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * Created by miron on 7. 12. 2014.
 */
public class ToolbarTransferHandler extends TransferHandler {

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


}
