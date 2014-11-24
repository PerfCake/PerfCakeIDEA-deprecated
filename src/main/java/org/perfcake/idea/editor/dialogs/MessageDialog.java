package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.dialogs.tables.HeaderEditor;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.editor.dialogs.tables.ValidatorSenderEditor;
import org.perfcake.idea.model.Message;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 16. 11. 2014.
 */
public class MessageDialog extends DialogWrapper {
    private JTextField uriTextField;
    private JTextField multiplicityTextField;
    private JTextField contentTextField;
    private PropertiesEditor propertiesEditor;
    private JPanel rootPanel;
    private ValidatorSenderEditor validatorSenderEditor;
    private HeaderEditor headerEditor;
    private MessageValidationPair mockPair;

    public MessageDialog(@NotNull Component parent, MessageValidationPair mockPair) {
        super(parent, true);
        this.mockPair = mockPair;
        init();
        setTitle("Edit Message");

        uriTextField.setText(mockPair.getMessage().getUri().getStringValue());
        multiplicityTextField.setText(mockPair.getMessage().getMultiplicity().getStringValue());
        contentTextField.setText(mockPair.getMessage().getContent().getStringValue());

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public MessageValidationPair getMockPair() {
        Message m = mockPair.getMessage();
        m.getUri().setStringValue(uriTextField.getText());
        m.getMultiplicity().setStringValue(multiplicityTextField.getText());
        m.getContent().setStringValue(contentTextField.getText());
        return mockPair;
    }

    private void createUIComponents() {
        headerEditor = new HeaderEditor(mockPair.getMessage());
        propertiesEditor = new PropertiesEditor(mockPair.getMessage());
        validatorSenderEditor = new ValidatorSenderEditor(mockPair);
    }
}
