package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 16. 11. 2014.
 */
public class ValidatorDialog extends DialogWrapper {
    private static final Logger LOG = Logger.getInstance(ValidatorDialog.class);

    private JComboBox validatorComboBox;
    private JTextField idTextField;
    private PropertiesEditor propertiesEditor;
    private JPanel rootPanel;
    private Validator mockCopy;

    public ValidatorDialog(Component parent, Validator mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Validator");
        idTextField.setText(mockCopy.getId().getStringValue());
    }

    private void createUIComponents() {
        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        DefaultComboBoxModel validators = null;
        try {
            validators = new DefaultComboBoxModel<String>(classProvider.findValidators());
        } catch (PerfCakeClassProviderException e) {
            LOG.error("Error finding validators for ValidatorDialog ComboBox", e);
        }
        validatorComboBox = new ComboBox(validators);
        //set selected sender from model
        String modelValue = mockCopy.getClazz().getStringValue();
        for (int i = 0; i < validators.getSize(); i++) {
            if (validators.getElementAt(i).equals(modelValue)) {
                validators.setSelectedItem(validators.getElementAt(i));
                break;
            }
        }

        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public Validator getMockCopy() {
        mockCopy.getClazz().setStringValue((String) validatorComboBox.getSelectedItem());
        mockCopy.getId().setStringValue(idTextField.getText());
        //properties are already set to mockCopy by properties editor
        return mockCopy;
    }

    @Override
    public ValidationInfo doValidate() {
        if (idTextField.getText().trim().isEmpty()) {
            return new ValidationInfo("Id can't be empty.", idTextField);
        }
        return null;
    }
}
