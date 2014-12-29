package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Header;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 18. 11. 2014.
 */
public class HeaderDialog extends MyDialogWrapper {
    private Header mockCopy;
    private JTextField nameTextField;
    private JTextField valueTextField;
    private JPanel rootPanel;

    public HeaderDialog(@NotNull Component parent, Header mockCopy, Mode mode) {
        super(parent, true);
        init();
        setTitle(mode == Mode.ADD ? "Add Header" : "Edit Header");

        this.mockCopy = mockCopy;

        nameTextField.setText(mockCopy.getName().getStringValue());
        valueTextField.setText(mockCopy.getValue().getStringValue());
    }

    public Header getMockCopy() {
        mockCopy.getName().setStringValue(nameTextField.getText());
        mockCopy.getValue().setStringValue(valueTextField.getText());
        return mockCopy;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }


    protected ValidationInfo doValidate() {
        if (nameTextField.getText().trim().isEmpty()) {
            return new ValidationInfo("Please specify header name", nameTextField);
        }
        if (valueTextField.getText().trim().isEmpty()) {
            return new ValidationInfo("Please specify header value", valueTextField);
        }
        return null;
    }
}
