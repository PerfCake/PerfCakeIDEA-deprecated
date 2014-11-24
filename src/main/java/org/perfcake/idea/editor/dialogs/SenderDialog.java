package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.Sender;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 15. 11. 2014.
 */
public class SenderDialog extends DialogWrapper {

    private static final Logger LOG = Logger.getInstance(SenderDialog.class);

    private JPanel rootPanel;
    private JComboBox senderComboBox;
    private PropertiesEditor propertiesEditor;
    private Sender mockCopy;

    public SenderDialog(Component parent, Sender mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Sender");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public Sender getMockCopy() {
        mockCopy.getClazz().setStringValue((String) senderComboBox.getSelectedItem());
        return mockCopy;
    }

    private void createUIComponents() {
        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        DefaultComboBoxModel senders = null;
        try {
            senders = new DefaultComboBoxModel<String>(classProvider.findSenders());
        } catch (PerfCakeClassProviderException e) {
            LOG.error("Error finding senders for SenderDialog ComboBox", e);
        }
        senderComboBox = new ComboBox(senders);
        //set selected sender from model
        String modelValue = mockCopy.getClazz().getStringValue();
        for (int i = 0; i < senders.getSize(); i++) {
            if (senders.getElementAt(i).equals(modelValue)) {
                senders.setSelectedItem(senders.getElementAt(i));
                break;
            }
        }

        propertiesEditor = new PropertiesEditor(mockCopy);
    }
}
