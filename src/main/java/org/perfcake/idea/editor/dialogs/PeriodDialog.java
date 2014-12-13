package org.perfcake.idea.editor.dialogs;

import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Period;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 19. 11. 2014.
 */
public class PeriodDialog extends MyDialogWrapper {
    private JTextField periodTextField;
    private JTextField valueTextField;
    private JPanel rootPanel;
    private Period mockCopy;

    public PeriodDialog(Component parent, Period mockCopy) {
        super(parent, true);
        this.mockCopy = mockCopy;
        init();
        setTitle("Edit Period");

        this.mockCopy = mockCopy;

        periodTextField.setText(mockCopy.getType().getStringValue());
        valueTextField.setText(mockCopy.getValue().getStringValue());
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return rootPanel;
    }

    public Period getMockCopy() {
        mockCopy.getType().setStringValue(periodTextField.getText());
        mockCopy.getValue().setStringValue(valueTextField.getText());
        return mockCopy;
    }
}
