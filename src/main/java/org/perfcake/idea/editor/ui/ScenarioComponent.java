package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.swing.JRoundedRectangle;
import org.perfcake.idea.model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioComponent extends BasicDomElementComponent<Scenario> {
    private JPanel scenarioPanel;
    private EditorPanel editorPanel;

    public ScenarioComponent(Scenario domElement) {
        super(domElement);

        initScenarioPanel();
        initEditorPanel();
    }


    @Override
    public JComponent getComponent() {
        return editorPanel;
    }

    private void initScenarioPanel() {
        scenarioPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        GeneratorComponent generatorComponent = new GeneratorComponent(myDomElement.getGenerator());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        scenarioPanel.add(generatorComponent.getComponent(), constraints);
        addComponent(generatorComponent);

        SenderComponent senderComponent = new SenderComponent(myDomElement.getSender());
        constraints.gridx = 0;
        constraints.gridy = 1;
        scenarioPanel.add(senderComponent.getComponent(), constraints);

        MessagesComponent messagesComponent = new MessagesComponent(myDomElement.getMessages());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        scenarioPanel.add(messagesComponent.getComponent(), constraints);

        ValidationComponent validationComponent = new ValidationComponent(myDomElement.getValidation());
        constraints.gridx = 0;
        constraints.gridy = 3;
        scenarioPanel.add(validationComponent.getComponent(), constraints);

        ReportingComponent reportingComponent = new ReportingComponent(myDomElement.getReporting());
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        scenarioPanel.add(reportingComponent.getComponent(), constraints);

        PropertiesComponent propertiesComponent = new PropertiesComponent(myDomElement.getProperties());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        scenarioPanel.add(propertiesComponent.getComponent(), constraints);
        addComponent(propertiesComponent);

        scenarioPanel.addMouseListener(new MyMouseAdapter());

    }

    private void initEditorPanel() {
        editorPanel = new EditorPanel(new JPanel(), scenarioPanel);
    }

    private class MyMouseAdapter extends MouseAdapter {
        private JRoundedRectangle selected = null;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selected != null) {
                selected.deselect();
                selected = null;
            }
            synchronized (getComponent().getTreeLock()) {
                traverseComponents(e, getComponent().getComponents());
            }
            if (selected != null) {
                selected.select();
            }

            if (e.getClickCount() == 2 && selected != null) {
                //selected.invokeDialog(scenarioPsi);
            }
        }

        private void traverseComponents(MouseEvent e, Component[] components) {
            for (Component c : components) {
                Point convertPoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), c);
                if (c.contains(convertPoint)) {
                    if (c instanceof JRoundedRectangle) {
                        selected = (JRoundedRectangle) c;
                    }
                    traverseComponents(e, ((Container) c).getComponents());
                }
            }
        }
    }

}
