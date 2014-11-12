package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.5.2014.
 */
public class PropertyEditDialog extends DialogWrapper {
    private JTextField nameTextField;
    private JTextField valueTextField;
    private JPanel rootPanel;

    private Property mockCopy;

    public PropertyEditDialog(@NotNull Component parent, Property mockCopy) {
        super(parent, true);
        init();
        setTitle("Edit Property");

        this.mockCopy = mockCopy;

        nameTextField.setText(mockCopy.getName().getStringValue());
        valueTextField.setText(mockCopy.getValue().getStringValue());
    }

    public Property getMockCopy() {
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
        /*if (getNameText().trim().isEmpty()) {
            return new ValidationInfo("Please specify property name", nameTextField);
        }
        if (getValueText().trim().isEmpty()) {
            return new ValidationInfo("Please specify property value", valueTextField);
        }*/
        return null;
    }
}
