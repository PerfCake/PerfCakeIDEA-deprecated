package org.perfcake.idea.module;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.util.PerfCakeClassProvider;

import javax.swing.*;

/**
 * Created by miron on 11.3.2014.
 */
public class NewScenarioDialog extends DialogWrapper {
    private JPanel myRootPanel;
    private JTextField nameField;
    private JComboBox generatorComboBox;
    private JComboBox senderComboBox;

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myRootPanel;
    }

    public NewScenarioDialog(@Nullable Project project) {
        super(project);
        init();
        setTitle("New Scenario");

        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        final DefaultComboBoxModel generators = new DefaultComboBoxModel(classProvider.findGenerators());
        generatorComboBox.setModel(generators);

        final DefaultComboBoxModel senders = new DefaultComboBoxModel(classProvider.findSenders());
        senderComboBox.setModel(senders);
    }

    public String getName() {
        return nameField.getText();
    }


    public String getGeneratorName() {
        return generatorComboBox.getSelectedItem().toString();
    }

    public String getSenderName() {
        return senderComboBox.getSelectedItem().toString();
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
