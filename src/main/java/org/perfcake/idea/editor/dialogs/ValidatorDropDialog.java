package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Validator;

import javax.swing.*;

/**
 * Created by miron on 8. 12. 2014.
 */
public class ValidatorDropDialog extends MyDialogWrapper {

    private Validator validator;
    private JPanel rootPanel;
    private JTextField idTextField;

    public ValidatorDropDialog(Validator validator) {
        super(false);
        this.validator = validator;

        init();
        setTitle("Add Validator");
    }

    @Override
    public Validator getMockCopy() {
        validator.getId().setStringValue(idTextField.getText());
        return validator;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (idTextField.getText().isEmpty()) {
            return new ValidationInfo("Validator Id cannot be empty.", idTextField);
        }
        return null;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return idTextField;
    }
}
