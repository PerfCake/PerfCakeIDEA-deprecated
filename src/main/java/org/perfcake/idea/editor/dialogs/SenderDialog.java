package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ValidationInfo;
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
public class SenderDialog extends MyDialogWrapper {

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

    public SenderDialog(Sender mockCopy) {
        super(true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Sender");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return getRootPanel();
    }

    public JComponent getRootPanel() {
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
        senders.setSelectedItem(modelValue);

        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (senderComboBox.getSelectedItem() == null) {
            return new ValidationInfo("Please specify sender", senderComboBox);
        }
        return null;
    }
}
