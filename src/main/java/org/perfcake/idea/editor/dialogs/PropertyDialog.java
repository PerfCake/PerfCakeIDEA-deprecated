package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Property;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.5.2014.
 */
public class PropertyDialog extends MyDialogWrapper {
    private JTextField nameTextField;
    private JTextField valueTextField;
    private JPanel rootPanel;

    private Property mockCopy;

    public PropertyDialog(@NotNull Component parent, Property mockCopy, Mode mode) {
        super(parent, true);
        init(mockCopy, mode);
    }

    public PropertyDialog(Property mockCopy, Mode mode) {
        super(false);
        init(mockCopy, mode);
    }


    private void init(Property mockCopy, Mode mode) {
        super.init();
        setTitle(mode == Mode.ADD ? "Add Property" : "Edit Property");

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

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return nameTextField;
    }
}
