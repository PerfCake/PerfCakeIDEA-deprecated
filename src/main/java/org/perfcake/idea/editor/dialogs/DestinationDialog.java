package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PeriodEditor;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.util.PerfCakeClassProvider;
import org.perfcake.idea.util.PerfCakeClassProviderException;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 19. 11. 2014.
 */
public class DestinationDialog extends MyDialogWrapper {
    private static final Logger LOG = Logger.getInstance(DestinationDialog.class);
    private Destination mockCopy;
    private JComboBox destinationComboBox;
    private JCheckBox enabledCheckBox;
    private PeriodEditor periodEditor;
    private PropertiesEditor propertiesEditor;
    private JPanel rootPanel;

    public DestinationDialog(Component parent, Destination mockCopy, Mode mode) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        switch (mode) {
            case ADD:
                setTitle("Add Destination");
                break;
            case EDIT:
                setTitle("Edit Destination");
                break;
        }
        enabledCheckBox.setSelected(mockCopy.getEnabled().getValue() == null ? true : mockCopy.getEnabled().getValue());
        //set selected generator from model
        String modelValue = mockCopy.getClazz().getStringValue();
        ComboBoxModel destinations = destinationComboBox.getModel();
        destinations.setSelectedItem(modelValue);

    }

    private void createUIComponents() {
        //get available generators for combobox
        PerfCakeClassProvider classProvider = new PerfCakeClassProvider();
        DefaultComboBoxModel destinations = null;
        try {
            destinations = new DefaultComboBoxModel<String>(classProvider.findDestinations());
        } catch (PerfCakeClassProviderException e) {
            LOG.error("Error finding destinations for DestinationDialog ComboBox", e);
        }
        destinationComboBox = new ComboBox(destinations);

        periodEditor = new PeriodEditor(mockCopy);
        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public Destination getMockCopy() {
        mockCopy.getClazz().setStringValue((String) destinationComboBox.getSelectedItem());
        mockCopy.getEnabled().setStringValue(Boolean.toString(enabledCheckBox.isSelected()));
        return mockCopy;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (destinationComboBox.getSelectedItem() == null) {
            return new ValidationInfo("Please specify destination", destinationComboBox);
        }
        return null;
    }
}
