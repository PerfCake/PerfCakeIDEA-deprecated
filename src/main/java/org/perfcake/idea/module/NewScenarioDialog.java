package org.perfcake.idea.module;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by miron on 11.3.2014.
 */
public class NewScenarioDialog extends DialogWrapper {
    private JPanel myRootPanel;
    private JTextField nameField;
    private JTextField generatorField;
    private JTextField senderField;

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myRootPanel;
    }

    public NewScenarioDialog(@Nullable Project project) {
        super(project);
        init();
        setTitle("New Scenario");
    }

    public String getName() {
        return nameField.getText();
    }


    public String getGeneratorName() {
        return generatorField.getText();
    }

    public String getSenderName() {
        return senderField.getText();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (getName().trim().isEmpty()) {
            return new ValidationInfo("Scenario name must be specified", nameField);
        }
        return null;
    }
}
