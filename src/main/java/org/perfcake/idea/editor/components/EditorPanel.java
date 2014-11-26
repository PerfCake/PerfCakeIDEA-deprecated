package org.perfcake.idea.editor.components;

import com.intellij.ide.ui.customization.CustomActionsSchema;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.util.EditorPopupHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 22.10.2014.
 */
public class EditorPanel extends JPanel {
    JPanel toolbarPanel;
    ScenarioPanel scenarioPanel;

    public EditorPanel(JPanel toolbarPanel, ScenarioPanel scenarioPanel) {
        super(new BorderLayout());

        this.toolbarPanel = toolbarPanel;
        this.scenarioPanel = scenarioPanel;

        //TODO JBSplitter
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

    public JPanel getToolbarPanel() {
        return toolbarPanel;
    }

    public ScenarioPanel getScenarioPanel() {
        return scenarioPanel;
    }

}
