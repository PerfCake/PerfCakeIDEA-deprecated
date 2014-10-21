package org.perfcake.idea.editor.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 22.10.2014.
 */
public class EditorPanel extends JPanel {

    public EditorPanel(JPanel toolbarPanel, JPanel scenarioPanel) {
        super(new BorderLayout());
        //create resizable split pane for inner panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //splitPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));

        //set divider size
        //splitPane.setDividerSize(10);

        //set divider default location
        splitPane.setDividerLocation(200);

        //add panels to split pane
        splitPane.setLeftComponent(toolbarPanel);
        splitPane.setRightComponent(scenarioPanel);

        //add split pane to editor
        add(splitPane, BorderLayout.CENTER);
    }
}
