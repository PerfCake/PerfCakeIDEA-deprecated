package org.perfcake.idea.editor.ui.components;

import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.GeneratorModel;
import org.perfcake.idea.model.RunModel;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 30.4.2014.
 */
public class GeneratorRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {

    private static final Logger LOG = Logger.getInstance(GeneratorRectangle.class);

    private GeneratorModel model;

    private RunLabel runLabel;

    public GeneratorRectangle(GeneratorModel model) {
        super(model.getGenerator().getClazz() + " (" + model.getGenerator().getThreads() + ")");
        this.model = model;
        model.addPropertyChangeListener(this);


        RunModel runModel = new RunModel(model.getGenerator().getRun());
        runLabel = new RunLabel(runModel);
        panel.add(runLabel);

        /*for (Property p : model.getGenerator().getProperty()) {
            PropertyModel propertyModel = new PropertyModel(p);
            PropertyRectangle propertyRectangle = new PropertyRectangle(propertyModel);
            propertyRectangle.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            panel.add(propertyRectangle);
        }*/
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        if (property.equals(GeneratorModel.RUN_PROPERTY)) {
            runLabel.getModel().setRun((Scenario.Generator.Run) evt.getNewValue());
        }
        if (property.equals(GeneratorModel.CLAZZ_PROPERTY) || property.equals(GeneratorModel.THREADS_PROPERTY)) {
            label.setText(model.getGenerator().getClazz() + " (" + model.getGenerator().getThreads() + ")");
        }
        if(property.equals(GeneratorModel.GENERATOR_PROPERTY)){
            updateRectangle();
        }
    }

    private void updateRectangle(){
        label.setText(model.getGenerator().getClazz() + " (" + model.getGenerator().getThreads() + ")");
        runLabel.getModel().setRun(model.getGenerator().getRun());
    }
}
