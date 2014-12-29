package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.DocumentAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.components.MessageValidationPair;
import org.perfcake.idea.editor.dialogs.tables.HeaderEditor;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.editor.dialogs.tables.ValidatorMessageEditor;
import org.perfcake.idea.model.Message;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
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
    private ValidatorMessageEditor validatorMessageEditor;
    private HeaderEditor headerEditor;
    private JRadioButton uriRadioButton;
    private JRadioButton contentRadioButton;
    private ButtonGroup group;// = new ButtonGroup();
    private MessageValidationPair mockPair;
    private boolean forceEmptyContent = false;

    public MessageDialog(@NotNull Component parent, final MessageValidationPair mockPair, Mode mode) {
        super(parent, true);
        this.mockPair = mockPair;
        init();
        setTitle(mode == Mode.ADD ? "Add Message" : "Edit Message");

        uriTextField.setText(mockPair.getMessage().getUri().getStringValue());
        multiplicityTextField.setText(mockPair.getMessage().getMultiplicity().getStringValue());
        contentTextField.setText(mockPair.getMessage().getContent().getStringValue());

        group = new ButtonGroup();
        group.add(uriRadioButton);
        group.add(contentRadioButton);

        uriRadioButton.setEnabled(false);
        contentRadioButton.setEnabled(false);

        if (mockPair.getMessage().getContent().exists()) {
            contentRadioButton.setSelected(true);
        } else if (!uriTextField.getText().isEmpty()) {
            uriRadioButton.setSelected(true);
        }

        uriTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                if (contentTextField.getText().isEmpty() && !mockPair.getMessage().getContent().exists()) {
                    if (e.getDocument().getLength() > 0) {
                        uriRadioButton.setSelected(true);
                    } else {
                        group.clearSelection();
                    }
                }
            }
        });

        contentTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                if (e.getDocument().getLength() > 0 || mockPair.getMessage().getContent().exists()) {
                    contentRadioButton.setSelected(true);
                } else {
                    if (!uriTextField.getText().isEmpty()) {
                        uriRadioButton.setSelected(true);
                    } else {
                        group.clearSelection();
                    }
                }
            }
        });

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public MessageValidationPair getMockPair() {
        Message m = mockPair.getMessage();
        if(!uriTextField.getText().isEmpty()) m.getUri().setStringValue(uriTextField.getText());
        if(!multiplicityTextField.getText().isEmpty()) m.getMultiplicity().setStringValue(multiplicityTextField.getText());
        if (!contentTextField.getText().isEmpty() || forceEmptyContent)
            m.getContent().setStringValue(contentTextField.getText());
        return mockPair;
    }

    private void createUIComponents() {
        headerEditor = new HeaderEditor(mockPair.getMessage());
        propertiesEditor = new PropertiesEditor(mockPair.getMessage());
        validatorMessageEditor = new ValidatorMessageEditor(mockPair);
    }


    @Override
    protected void doOKAction() {
        if(uriTextField.getText().isEmpty() && contentTextField.getText().isEmpty()){
            if (Messages.showYesNoDialog(rootPanel,
                    "Do you want to create message with empty content?",
                    "None Of Message URI Or Content Specified",
                    null) == Messages.YES) {
                forceEmptyContent = true;
                super.doOKAction();
            } else {
                return;
            }
        }
        super.doOKAction();
    }
}
