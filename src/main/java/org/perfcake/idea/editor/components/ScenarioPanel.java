package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.Committable;
import org.perfcake.idea.editor.colors.ColorComponents;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.ColorAdjustable;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.model.ValidatorRef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miron on 2.11.2014.
 */
public class ScenarioPanel extends JPanel implements ColorAdjustable {
    MessagesComponent messagesComponent;
    ValidationComponent validationComponent;
    private ScenarioComponent scenarioComponent;

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
        addMouseListener(new PopClickListener(scenarioComponent.getDomElement(), ScenarioPanel.this));
        init();
        updateColors();
    }

    private Map<JComponent, List<JComponent>> initMessageValidators() {
        Map<JComponent, java.util.List<JComponent>> messageValidators = new HashMap<>();

        List<Committable> messageComponents = messagesComponent.getChildren();

        for (int i = 0; i < messageComponents.size(); i++) {
            MessageComponent messageComponent = (MessageComponent) messageComponents.get(i);
            Message m = messageComponent.getDomElement();
            if (!m.isValid()) continue;

            List<ValidatorRef> validatorRefs = m.getValidatorRefs();
            if (!validatorRefs.isEmpty()) {
                for (ValidatorRef vRef : validatorRefs) {
                    if (!vRef.isValid()) continue;

                    List<Committable> validatorComponents = validationComponent.getChildren();
                    for (int j = 0; j < validatorComponents.size(); j++) {
                        ValidatorComponent validatorComponent = (ValidatorComponent) validatorComponents.get(j);
                        Validator v = validatorComponent.getDomElement();
                        if (!v.isValid()) continue;

                        if (v.getId().getStringValue() != null && vRef.getId().getStringValue() != null) {
                            if (v.getId().getStringValue().equals(vRef.getId().getStringValue())) {
                                List<JComponent> componentList = messageValidators.get(messageComponent.getComponent());
                                if (componentList == null) {
                                    componentList = new ArrayList<>();
                                }
                                componentList.add(validatorComponent.getComponent());
                                messageValidators.put(messageComponent.getComponent(), componentList);
                            }
                        }
                    }
                }
            }
        }
        return messageValidators;
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

        messagesComponent = new MessagesComponent(scenarioComponent.getDomElement().getMessages());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(messagesComponent.getComponent(), constraints);
        scenarioComponent.addComponent(messagesComponent);

        validationComponent = new ValidationComponent(scenarioComponent.getDomElement().getValidation());
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(validationComponent.getComponent(), constraints);
        scenarioComponent.addComponent(validationComponent);

        ReportingComponent reportingComponent = new ReportingComponent(scenarioComponent.getDomElement().getReporting());
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        //constraints.weighty = 0.5;
        add(reportingComponent.getComponent(), constraints);
        scenarioComponent.addComponent(reportingComponent);

        PropertiesComponent propertiesComponent = new PropertiesComponent(scenarioComponent.getDomElement().getProperties());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        //constraints.weighty = 1.0;
        add(propertiesComponent.getComponent(), constraints);
        scenarioComponent.addComponent(propertiesComponent);

    }

    public Map<JComponent, List<JComponent>> getMessageValidators() {
        return initMessageValidators();
    }
}
