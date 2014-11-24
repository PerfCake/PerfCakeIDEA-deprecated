package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.model.Properties;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21.5.2014.
 */
public class PropertiesDialog extends DialogWrapper {
    private Properties mockCopy;
    private JPanel rootPanel;
    private PropertiesEditor propertiesEditor;


    public PropertiesDialog(@NotNull Component parent, final Properties mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;

        init();
        setTitle("Edit properties");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    public Properties getMockCopy() {
        return mockCopy;
    }

}
