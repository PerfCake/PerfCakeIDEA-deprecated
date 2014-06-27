package org.perfcake.idea.editor.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 9.6.2014.
 */
public class ScenarioEditorInvalidPanel extends JPanel {

    public ScenarioEditorInvalidPanel() {
        super(new BorderLayout());
        JLabel error = new JLabel("Scenario in Text Editor cannot be parsed with PerfCake. Please return to Text Editor and fix scneario first.");
        error.setHorizontalAlignment(JLabel.CENTER);
        add(error);
    }
}
