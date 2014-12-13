package org.perfcake.idea.editor.dialogs;

import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.tables.PropertiesEditor;
import org.perfcake.idea.editor.dialogs.tables.ReporterEditor;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21. 11. 2014.
 */
public class ReportingDialog extends MyDialogWrapper {
    private Reporting mockCopy;
    private ReporterEditor reporterEditor;
    private JPanel rootPanel;
    private PropertiesEditor propertiesEditor;

    public ReportingDialog(Component parent, Reporting mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Reporting");
    }

    public ReportingDialog(Reporting mockCopy) {
        super(true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Reporting");
    }

    private void createUIComponents() {
        reporterEditor = new ReporterEditor(mockCopy);
        propertiesEditor = new PropertiesEditor(mockCopy);
    }

    @Nullable
    @Override
    public JComponent createCenterPanel() {
        return rootPanel;
    }

    public Reporting getMockCopy() {
        return mockCopy;
    }
}
