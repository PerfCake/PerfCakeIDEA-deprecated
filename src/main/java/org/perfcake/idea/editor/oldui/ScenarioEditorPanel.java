package org.perfcake.idea.editor.oldui;

import com.intellij.psi.PsiFile;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 19.4.2014.
 */
public class ScenarioEditorPanel extends JPanel {
    private Scenario scenario;
    private PsiFile scenarioPsi;

    private JSplitPane validPanel;
    private ScenarioEditorInvalidPanel invalidPanel;

    private Boolean isValidShowed;


    /**
     * Constructs invalid Editor panel
     */
    public ScenarioEditorPanel(PsiFile scenarioPsi) {
        this.scenarioPsi = scenarioPsi;

        //set layout to allow panel to take whole area
        setLayout(new BorderLayout());

        initInvalidPanel();
    }

    /**
     * Constructs valid Editor panel for scenario
     *
     * @param scenario valid scenario oldmodel to show
     */
    public ScenarioEditorPanel(PsiFile scenarioPsi, Scenario scenario) {
        this.scenarioPsi = scenarioPsi;

        //set layout to allow panel to take whole area
        setLayout(new BorderLayout());

        initValidPanel(scenario);
    }

    private void initInvalidPanel() {
        invalidPanel = new ScenarioEditorInvalidPanel();
        add(invalidPanel, BorderLayout.CENTER);
        isValidShowed = Boolean.FALSE;
    }

    private void initValidPanel(Scenario scenario) {
        this.scenario = scenario;

        //create resizable split pane for inner panels
        validPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //splitPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));

        //set divider size
        //splitPane.setDividerSize(10);

        //set divider default location
        validPanel.setDividerLocation(200);

        //create new scenario toolbar panel which will be positioned on the left side
        ScenarioEditorToolbarPanel toolbarPanel = new ScenarioEditorToolbarPanel();

        //create new scenario content panel on the right side
        ScenarioEditorContentPanel contentPanel = new ScenarioEditorContentPanel(scenarioPsi, scenario);

        //add panels to split pane
        validPanel.setLeftComponent(toolbarPanel);
        validPanel.setRightComponent(contentPanel);

        //add panel to editor
        add(validPanel, BorderLayout.CENTER);
        isValidShowed = Boolean.TRUE;
    }

    /**
     * Shows scenarioModel in this Scenario Editor.
     * If the oldmodel is the same as it was before, Editor content remains.
     * If the oldmodel is new, Editor content is updated.
     */
    public void showValidPanel(Scenario scenarioModel) {
        if (this.scenario == null) {
            //valid panel was never showed, we need to remove invalid one and init valid panel.
            remove(invalidPanel);
            initValidPanel(scenarioModel);
        } else {
            //valid panel exists already
            if (this.scenario == scenarioModel) {
                //oldmodel from method parameter is the same as ours, we just need to show valid panel.
                if (isValidShowed) {
                    return;
                } else {
                    //Invalid panel is showed. Just show valid one, because models are same.
                    remove(invalidPanel);
                    //Valid panel exists, because this.scenario is not null, so the valid panel had to be initialized already.
                    add(validPanel, BorderLayout.CENTER);
                    isValidShowed = Boolean.TRUE;
                }
            } else {
                //new oldmodel, we need to update valid editor.
                this.scenario = scenarioModel;
                ((ScenarioEditorContentPanel) validPanel.getRightComponent()).setModelAndRefresh(scenarioPsi, this.scenario);

                if (isValidShowed) {
                    return;
                } else {
                    remove(invalidPanel);
                    add(validPanel, BorderLayout.CENTER);
                    isValidShowed = Boolean.TRUE;
                }
            }
        }
    }

    /**
     * Shows Editor with error message
     */
    public void showInvalidPanel() {
        if (isValidShowed) {
            remove(validPanel);
            if (invalidPanel == null) {
                initInvalidPanel();
            } else {
                remove(validPanel);
                add(invalidPanel, BorderLayout.CENTER);
                isValidShowed = Boolean.FALSE;
            }
        }
    }

}
