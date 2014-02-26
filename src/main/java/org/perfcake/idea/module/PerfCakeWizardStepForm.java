package org.perfcake.idea.module;

import javax.swing.*;

/**
 * Created by miron on 25.2.2014.
 */
public class PerfCakeWizardStepForm {
    private JTextField generatorField;
    private JTextField senderField;
    private JPanel rootPanel;

    public String getGenerator() {
        return generatorField.getText();
    }

    public String getSender() {
        return senderField.getText();
    }

    public JComponent getRootPanel() {
        return rootPanel;
    }
}
