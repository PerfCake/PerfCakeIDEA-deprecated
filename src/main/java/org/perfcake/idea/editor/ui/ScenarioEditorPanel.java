package org.perfcake.idea.editor.ui;

import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 19.4.2014.
 */
public class ScenarioEditorPanel extends JPanel {

    public ScenarioEditorPanel(VirtualFile scenario) {
        //set layout to allow split pane take whole area
        setLayout(new BorderLayout());

        //create resizable split pane for panels
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //splitPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));

        //set divider size
        //splitPane.setDividerSize(10);

        //set divider default location
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        //create new scenario toolbar panel which will be positioned on the left side
        ScenarioToolbarPanel toolbarPanel = new ScenarioToolbarPanel();

        //create new scenario file editor panel on the right side
        ScenarioFileEditorPanel fileEditorPanel = new ScenarioFileEditorPanel(scenario);

        //add panels to split pane
        splitPane.setLeftComponent(toolbarPanel);
        splitPane.setRightComponent(fileEditorPanel);
    }
}
