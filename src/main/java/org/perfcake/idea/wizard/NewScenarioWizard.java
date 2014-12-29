package org.perfcake.idea.wizard;

import com.intellij.ide.wizard.AbstractWizard;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

/**
 * Created by miron on 11. 12. 2014.
 */
public class NewScenarioWizard extends AbstractWizard {


    private final Scenario scenario;

    NameStep nameStep;
    GeneratorStep generatorStep;
    SenderStep senderStep;
    MessagesStep messagesStep;
    ValidationStep validationStep;
    ReportingStep reportingStep;
    PropertiesStep propertiesStep;

    public NewScenarioWizard(@Nullable Project project, Scenario scenario) {
        super("Specify scenario name", project);
        this.scenario = scenario;

        nameStep = new NameStep(this);
        generatorStep = new GeneratorStep(scenario.getGenerator(), this);
        senderStep = new SenderStep(scenario.getSender(), this);
        messagesStep = new MessagesStep(PerfCakeIdeaUtil.getMessagesValidationPair(scenario.getMessages()), this);
        validationStep = new ValidationStep(scenario.getValidation(), this);
        reportingStep = new ReportingStep(scenario.getReporting(), this);
        propertiesStep = new PropertiesStep(scenario.getProperties(), this);

        addStep(nameStep);
        addStep(generatorStep);
        addStep(senderStep);
        addStep(messagesStep);
        addStep(validationStep);
        addStep(reportingStep);
        addStep(propertiesStep);

        init();
        getHelpButton().setVisible(false);
    }

    @Nullable
    @Override
    protected String getHelpID() {
        return null;
    }

    @Override
    protected boolean canFinish() {
        return !nameStep.getScenarioName().isEmpty() && generatorStep.isComplete();
    }

//    @Override
//    protected void updateButtons() {
//        if (isLastStep()) {
//            if (mySteps.size() > 1) {
//                getNextButton().setText(UIUtil.removeMnemonic(IdeBundle.message("button.finish")));
//                getNextButton().setMnemonic('F');
//            }
//            else {
//                getNextButton().setText(IdeBundle.message("button.ok"));
//            }
//            getNextButton().setEnabled(canFinish());
//        }
//        else {
//            getNextButton().setText(UIUtil.removeMnemonic(IdeBundle.message("button.wizard.next")));
//            getNextButton().setMnemonic('N');
//            getNextButton().setEnabled(canGoNext());
//        }
//
//        if (getNextButton().isEnabled() && !ApplicationManager.getApplication().isUnitTestMode()) {
//            getRootPane().setDefaultButton(getNextButton());
//        }
//
//        getNextButton().setEnabled(myCurrentStep > 0);
//    }
}
