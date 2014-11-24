package org.perfcake.idea.editor.components;

import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.swing.ColorAdjustable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 2.11.2014.
 */
public class ScenarioPanel extends JPanel implements ColorAdjustable {
    ScenarioComponent scenarioComponent;

    public ScenarioPanel(ScenarioComponent scenarioComponent) {
        super(new GridBagLayout());
        this.scenarioComponent = scenarioComponent;
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        init();
        updateColors();
    }

    @Override
    public void updateColors() {
        setForeground(ColorComponents.getColor(ColorType.SCENARIO_FOREGROUND));
        setBackground(ColorComponents.getColor(ColorType.SCENARIO_BACKGROUND));
    }

    private void init() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        GeneratorComponent generatorComponent = new GeneratorComponent(scenarioComponent.getDomElement().getGenerator());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        add(generatorComponent.getComponent(), constraints);
        scenarioComponent.addComponent(generatorComponent);

        SenderComponent senderComponent = new SenderComponent(scenarioComponent.getDomElement().getSender());
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(senderComponent.getComponent(), constraints);
        scenarioComponent.addComponent(senderComponent);

        MessagesComponent messagesComponent = new MessagesComponent(scenarioComponent.getDomElement().getMessages());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(messagesComponent.getComponent(), constraints);
        scenarioComponent.addComponent(messagesComponent);

        ValidationComponent validationComponent = new ValidationComponent(scenarioComponent.getDomElement().getValidation());
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(validationComponent.getComponent(), constraints);
        scenarioComponent.addComponent(validationComponent);

        ReportingComponent reportingComponent = new ReportingComponent(scenarioComponent.getDomElement().getReporting());
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(reportingComponent.getComponent(), constraints);
        scenarioComponent.addComponent(reportingComponent);

        PropertiesComponent propertiesComponent = new PropertiesComponent(scenarioComponent.getDomElement().getProperties());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(propertiesComponent.getComponent(), constraints);
        scenarioComponent.addComponent(propertiesComponent);
    }
}
