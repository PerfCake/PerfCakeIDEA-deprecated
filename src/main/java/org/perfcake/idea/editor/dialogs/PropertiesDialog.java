package org.perfcake.idea.editor.dialogs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.IProperties;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.5.2014.
 */
public class PropertiesDialog extends MyDialogWrapper {
    private IProperties mockCopy;
    private JPanel rootPanel;
    private PropertiesEditor propertiesEditor;


    public PropertiesDialog(@NotNull Component parent, final IProperties mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;

        init();
        setTitle("Edit properties");
    }

    public PropertiesDialog(final IProperties mockCopy) {
        super(true);
        this.mockCopy = mockCopy;

        init();
        setTitle("Edit properties");
    }

    @Nullable
    @Override
    public JComponent createCenterPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    public IProperties getMockCopy() {
        return mockCopy;
    }

}
