package org.perfcake.idea.editor.ui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import org.perfcake.PerfCakeException;
import org.perfcake.idea.editor.model.*;
import org.perfcake.idea.util.ScenarioHandler;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 15.4.2014.
 * Main panel for scenario editing
 */
public class ScenarioFileEditorPanel extends JPanel {
    private static final Logger LOG = Logger.getInstance(ScenarioFileEditorPanel.class);
    private Scenario scenario;
    private final ScenarioFileEditorPanel me = this;


    public ScenarioFileEditorPanel(final VirtualFile scenarioFile) {
        ScenarioHandler handler = null;
        try {
            handler = new ScenarioHandler(scenarioFile.getPath());
        } catch (PerfCakeException e) {
            e.printStackTrace();
        }
        this.scenario = handler.getScenarioModel();

        setLayout(new GridLayout(3, 2));

        GeneratorModel generatorModel = new GeneratorModel(scenario.getGenerator());
        GeneratorRectangle generatorRectangle = new GeneratorRectangle(generatorModel);
        add(generatorRectangle);

        SenderModel senderModel = new SenderModel(scenario.getSender());
        SenderRectangle senderRectangle = new SenderRectangle(senderModel);
        add(senderRectangle);

        MessagesModel messagesModel = new MessagesModel(scenario.getMessages());
        MessagesRectangle messagesRectangle = new MessagesRectangle(messagesModel);
        add(messagesRectangle);

        ValidationModel validationModel = new ValidationModel(scenario.getValidation());
        ValidationRectangle validationRectangle = new ValidationRectangle((validationModel));
        add(validationRectangle);

        PropertiesModel propertiesModel = new PropertiesModel(scenario);
        PropertiesRectangle propertiesRectangle = new PropertiesRectangle(propertiesModel);
        add(propertiesRectangle);




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
