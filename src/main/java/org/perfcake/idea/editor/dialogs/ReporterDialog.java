package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.DestinationEditor;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 20. 11. 2014.
 */
public class ReporterDialog extends MyDialogWrapper {
    private static final Logger LOG = Logger.getInstance(ReporterDialog.class);
    private Reporter mockCopy;
    private JComboBox reporterComboBox;
    private JCheckBox enabledCheckBox;
    private DestinationEditor destinationEditor;
    private PropertiesEditor propertiesEditor;
    private JPanel rootPanel;

    public ReporterDialog(Component parent, Reporter mockCopy, Mode mode) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        switch (mode) {
            case ADD:
                setTitle("Add Reporter");
                break;
            case EDIT:
                setTitle("Edit Reporter");
                enabledCheckBox.setSelected(mockCopy.getEnabled().getValue());
                //set selected reporter from model
                String modelValue = mockCopy.getClazz().getStringValue();
                ComboBoxModel reporters = reporterComboBox.getModel();
                for (int i = 0; i < reporters.getSize(); i++) {
                    if (reporters.getElementAt(i).equals(modelValue)) {
                        reporters.setSelectedItem(reporters.getElementAt(i));
                        break;
                    }
                }
                break;
        }
    }

    private void createUIComponents() {
        //get available generators for combobox
        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        DefaultComboBoxModel reporters = null;
        try {
            reporters = new DefaultComboBoxModel<String>(classProvider.findReporters());
        } catch (PerfCakeClassProviderException e) {
            LOG.error("Error finding destinations for DestinationDialog ComboBox", e);
        }
        reporterComboBox = new ComboBox(reporters);

        destinationEditor = new DestinationEditor(mockCopy);
        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public Reporter getMockCopy() {
        mockCopy.getClazz().setStringValue((String) reporterComboBox.getSelectedItem());
        mockCopy.getEnabled().setStringValue(Boolean.toString(enabledCheckBox.isSelected()));
        return mockCopy;
    }
}
