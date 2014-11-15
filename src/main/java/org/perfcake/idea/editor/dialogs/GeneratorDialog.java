package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Generator;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by miron on 14. 11. 2014.
 */
public class GeneratorDialog extends DialogWrapper {

    private static final Logger LOG = Logger.getInstance(GeneratorDialog.class);
    private JPanel rootPanel;
    private JComboBox runComboBox;
    private JComboBox generatorComboBox;
    private JTextField durationField;
    private JTextField threadsField;
    private PropertiesEditor propertiesEditor;
    private Generator mockCopy;

    public GeneratorDialog(Component parent, Generator mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Generator");

        durationField.setText(mockCopy.getRun().getValue().getStringValue());
        threadsField.setText(mockCopy.getThreads().getStringValue());

        durationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public Generator getMockCopy() {
        mockCopy.getClazz().setStringValue((String) generatorComboBox.getSelectedItem());
        mockCopy.getRun().getType().setStringValue((String) runComboBox.getSelectedItem());
        mockCopy.getRun().getValue().setStringValue(durationField.getText());
        mockCopy.getThreads().setStringValue(threadsField.getText());
        //propertiesEditor is working on our mockCopy's properties object, so we dont need set it back
        return mockCopy;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        //get available generators for combobox
        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        DefaultComboBoxModel generators = null;
        try {
            generators = new DefaultComboBoxModel<String>(classProvider.findGenerators());
        } catch (PerfCakeClassProviderException e) {
            LOG.error("Error finding generators for GeneratorDialog ComboBox", e);
        }
        generatorComboBox = new ComboBox(generators);
        //set selected generator from model
        String modelValue = mockCopy.getClazz().getStringValue();
        for (int i = 0; i < generators.getSize(); i++) {
            if (generators.getElementAt(i).equals(modelValue)) {
                generators.setSelectedItem(generators.getElementAt(i));
                break;
            }
        }

        String[] predefinedRunTypes = {
                "iteration",
                "time",
                "percentage"
        };
        runComboBox = new ComboBox(predefinedRunTypes);
        runComboBox.setSelectedItem(mockCopy.getRun().getType().getStringValue());
        runComboBox.setEditable(true);

        propertiesEditor = new PropertiesEditor(mockCopy);
    }

}
