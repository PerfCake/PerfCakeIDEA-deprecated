package org.perfcake.idea.editor.dragndrop;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 7. 12. 2014.
 */
public class ComponentTransferHandler extends TransferHandler {

    private final Map<String, DropAction> prefixActionMap;


    public ComponentTransferHandler(Map<String, DropAction> prefixActionMap) {
        this.prefixActionMap = prefixActionMap;
    }

    public ComponentTransferHandler(String prefix, DropAction dropAction) {
        prefixActionMap = new HashMap<>();
        prefixActionMap.put(prefix, dropAction);
    }

    public boolean canImport(TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        Transferable t = support.getTransferable();
        String data;
        try {
            data = (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }
        String[] split = data.split("~");
        if (split.length != 2) {
            return false;
        }
        if (!prefixActionMap.keySet().contains(split[0])) {
            return false;
        }
        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {
        Transferable t = support.getTransferable();
        String data;
        try {
            data = (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }
        String[] split = data.split("~");
        return prefixActionMap.get(split[0]).invoke(split[1]);
    }
}
