package org.perfcake.idea.module;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import javax.swing.*;
import java.io.File;

/**
 * Created by miron on 11.3.2014.
 */
public class NewScenarioDialog extends DialogWrapper {
    private static final Logger log = Logger.getInstance(NewScenarioDialog.class);

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
        setTitle("Scenario");

        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        try {
            final DefaultComboBoxModel generators = new DefaultComboBoxModel<String>(classProvider.findGenerators());
            generatorComboBox.setModel(generators);

            final DefaultComboBoxModel senders = new DefaultComboBoxModel<String>(classProvider.findSenders());
            senderComboBox.setModel(senders);
        } catch (PerfCakeClassProviderException e) {
            log.error("Error finding classes for combobox", e);
        }
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
        if (getName().contains(File.pathSeparator)) {
            return new ValidationInfo("Scenario name cannot contain " + File.pathSeparator, nameField);
        }
        return null;
    }
}
