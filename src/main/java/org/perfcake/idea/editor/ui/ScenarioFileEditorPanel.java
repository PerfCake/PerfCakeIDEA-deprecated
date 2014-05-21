package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import org.perfcake.PerfCakeException;
import org.perfcake.idea.editor.components.JRoundedRectangle;
import org.perfcake.idea.editor.model.*;
import org.perfcake.idea.util.ScenarioHandler;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 15.4.2014.
 * Main panel for scenario editing
 */
public class ScenarioFileEditorPanel extends JPanel {
    private static final Logger LOG = Logger.getInstance(ScenarioFileEditorPanel.class);
    private Scenario scenario;


    public ScenarioFileEditorPanel(final VirtualFile scenarioFile) {
        addMouseListener(new MouseAdapter() {
            private JRoundedRectangle selected = null;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (selected != null) {
                    selected.setSelected(Boolean.FALSE);
                    selected = null;
                }
                synchronized (getTreeLock()) {
                    traverseComponents(e, getComponents());
                }
                if (selected != null) {
                    selected.setSelected(Boolean.TRUE);
                    System.out.println(selected.toString());
                }

                if (e.getClickCount() == 2 && selected != null) {
                    selected.invokeDialog();
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

            private void doubleClick(MouseEvent e) {

            }
        });

        ScenarioHandler handler = null;
        try {
            System.out.println(scenarioFile.getPath());
            handler = new ScenarioHandler(scenarioFile.getPath());
        } catch (PerfCakeException e) {
            e.printStackTrace();
        }
        this.scenario = handler.getScenarioModel();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        GeneratorModel generatorModel = new GeneratorModel(scenario.getGenerator());
        GeneratorRectangle generatorRectangle = new GeneratorRectangle(generatorModel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        add(generatorRectangle, constraints);

        SenderModel senderModel = new SenderModel(scenario.getSender());
        SenderRectangle senderRectangle = new SenderRectangle(senderModel);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(senderRectangle, constraints);

        MessagesModel messagesModel = new MessagesModel(scenario.getMessages());
        MessagesRectangle messagesRectangle = new MessagesRectangle(messagesModel);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(messagesRectangle, constraints);

        ValidationModel validationModel = new ValidationModel(scenario.getValidation());
        ValidationRectangle validationRectangle = new ValidationRectangle((validationModel));
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(validationRectangle, constraints);

        ReportingModel reportingModel = new ReportingModel(scenario.getReporting());
        ReportingRectangle reportingRectangle = new ReportingRectangle((reportingModel));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(reportingRectangle, constraints);

        PropertiesModel propertiesModel = new PropertiesModel(scenario);
        PropertiesRectangle propertiesRectangle = new PropertiesRectangle(propertiesModel);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(propertiesRectangle, constraints);




        /*inal JTitledRoundedRectangle baseComponent1 = new JTitledRoundedRectangle("Generator");
        JRoundedRectangle baseComponent2 = new JRoundedRectangle();
        JRoundedRectangle baseComponent3 = new JRoundedRectangle();
        JRoundedRectangle baseComponent4 = new JRoundedRectangle();
        JRoundedRectangle baseComponent5 = new JRoundedRectangle();
        JRoundedRectangle baseComponent6 = new JRoundedRectangle();



        baseComponent1.setLayout(new BoxLayout(baseComponent1, BoxLayout.PAGE_AXIS));
        final JTextField generatorLabel = new JTextField("Generator");
        generatorLabel.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        generatorLabel.setFont(generatorLabel.getFont().deriveFont(22f));
        final ScenarioHandler finalHandler = handler;
        generatorLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SenderModel senderComponentModel = (SenderModel) ScenarioComponentFactory.buildScenarioComponent(scenario, ScenarioComponentType.SENDER);
                senderComponentModel.setSenderClazz(generatorLabel.getText());
                finalHandler.save();
            }
        });

        baseComponent1.add(generatorLabel);


        baseComponent1.setModel(ScenarioComponentFactory.buildScenarioComponent(this.scenario, ScenarioComponentType.SENDER));
        baseComponent1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                baseComponent1.label.setText((String) evt.getNewValue());
            }
        });


        add(baseComponent1);
        add(baseComponent2);
        add(baseComponent3);
        add(baseComponent4);
        add(baseComponent5);
        baseComponent5.add(baseComponent6);*/
    }


}
