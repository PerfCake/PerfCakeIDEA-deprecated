package org.perfcake.idea.editor.oldui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiFile;
import org.perfcake.idea.editor.components.JRoundedRectangle;
import org.perfcake.idea.editor.oldui.components.*;
import org.perfcake.idea.oldmodel.*;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 15.4.2014.
 * Main panel for scenario editing
 */
public class ScenarioEditorContentPanel extends JPanel {
    private static final Logger LOG = Logger.getInstance(ScenarioEditorContentPanel.class);
    GeneratorModel generatorModel;
    SenderModel senderModel;
    MessagesModel messagesModel;
    ValidationModel validationModel;
    ReportingModel reportingModel;
    PropertiesModel propertiesModel;
    private Scenario scenario;
    private PsiFile scenarioPsi;


    public ScenarioEditorContentPanel(PsiFile scenarioPsi, Scenario scenario) {
        this.scenario = scenario;
        this.scenarioPsi = scenarioPsi;

        initComponents();
        addMouseListener(new MyMouseAdapter());

        /*baseComponent1.setLayout(new BoxLayout(baseComponent1, BoxLayout.PAGE_AXIS));
        final JTextField generatorLabel = new JTextField("Generator");
        generatorLabel.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        generatorLabel.setFont(generatorLabel.getFont().deriveFont(22f));
        final ScenarioHandler finalHandler = handler;
        generatorLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SenderModel senderComponentModel = (SenderModel) ScenarioModelFactory.buildScenarioComponent(scenario, ScenarioModelType.SENDER);
                senderComponentModel.setSenderClazz(generatorLabel.getText());
                finalHandler.save();
            }
        });*/

    }

    public void setModelAndRefresh(PsiFile scenarioPsi, Scenario scenario) {
        this.scenario = scenario;
        this.scenarioPsi = scenarioPsi;

        generatorModel.setGenerator(scenario.getGenerator());
        senderModel.setSender(scenario.getSender());
        messagesModel.setMessages(scenario.getMessages());
        validationModel.setValidation(scenario.getValidation());
        reportingModel.setReporting(scenario.getReporting());
        propertiesModel.setProperties(scenario);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        generatorModel = new GeneratorModel(scenario.getGenerator());
        GeneratorRectangle generatorRectangle = new GeneratorRectangle(generatorModel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        add(generatorRectangle, constraints);

        senderModel = new SenderModel(scenario.getSender());
        SenderRectangle senderRectangle = new SenderRectangle(senderModel);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(senderRectangle, constraints);

        messagesModel = new MessagesModel(scenario.getMessages());
        MessagesRectangle messagesRectangle = new MessagesRectangle(messagesModel);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(messagesRectangle, constraints);

        validationModel = new ValidationModel(scenario.getValidation());
        ValidationRectangle validationRectangle = new ValidationRectangle((validationModel));
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(validationRectangle, constraints);

        reportingModel = new ReportingModel(scenario.getReporting());
        ReportingRectangle reportingRectangle = new ReportingRectangle((reportingModel));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(reportingRectangle, constraints);

        propertiesModel = new PropertiesModel(scenario);
        PropertiesRectangle propertiesRectangle = new PropertiesRectangle(propertiesModel);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(propertiesRectangle, constraints);
    }

    private class MyMouseAdapter extends MouseAdapter {
        private JRoundedRectangle selected = null;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selected != null) {
                selected.deselect();
                selected = null;
            }
            synchronized (getTreeLock()) {
                traverseComponents(e, getComponents());
            }
            if (selected != null) {
                selected.select();
            }

            if (e.getClickCount() == 2 && selected != null) {
                selected.invokeDialog(scenarioPsi);
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
