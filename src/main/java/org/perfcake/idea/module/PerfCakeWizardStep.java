package org.perfcake.idea.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

/**
 * Created by miron on 25.2.2014.
 */
public class PerfCakeWizardStep extends ModuleWizardStep {
    private PerfCakeModuleBuilder moduleBuilder;
    private PerfCakeWizardStepForm myForm;


    @Override
    public JComponent getComponent() {
        return myForm.getRootPanel();
    }

    @Override
    public void updateDataModel() {
        if (myForm.getGenerator() != null && !myForm.getGenerator().isEmpty()) {
            moduleBuilder.setGenerator(myForm.getGenerator());
        }
        if (myForm.getSender() != null && !myForm.getSender().isEmpty()) {
            moduleBuilder.setSender(myForm.getSender());
        }
    }

    public PerfCakeWizardStep(PerfCakeModuleBuilder moduleBuilder) {
        this.moduleBuilder = moduleBuilder;
        myForm = new PerfCakeWizardStepForm();

    }
}
