package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.perfcake.model.Property;

import javax.swing.*;
import java.util.List;

/**
 * Created by miron on 21.5.2014.
 */
public class PropertiesEditDialog extends DialogWrapper {
    private JTable propertiesTable;
    private JPanel rootPanel;

    public PropertiesEditDialog(@Nullable Project project, List<Property> properties) {
        super(project);
        init();
        setTitle("Edit properties");
        propertiesTable.getModel();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }
}
