package org.perfcake.idea.module;

import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.util.ScenarioHandler;
import org.perfcake.model.Scenario;

import java.io.File;

/**
 * Created by miron on 10.3.2014.
 */
public class NewScenarioAction extends CreateElementActionBase {
    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
        NewScenarioDialog scenarioDialog = new NewScenarioDialog(project);
        scenarioDialog.show();
        if (scenarioDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            //vytvor scenar so specifikovanymi hodnotami v adresari Scenarios
            try {
                final String path = project.getBaseDir().findChild("Scenarios").getPath() + File.separator + scenarioDialog.getName() + ".xml";
                final ScenarioHandler handler = new ScenarioHandler((new File(path)).toURI().toURL());
                Scenario model = handler.getScenarioModel();
                model.getGenerator().setClazz(scenarioDialog.getGeneratorName());
                model.getSender().setClazz(scenarioDialog.getSenderName());
                handler.save();
                //return new PsiElement[]{PerfCakeFileTemplates.createFromTemplate(PerfCakeFileTemplates.SCENARIO, scenarioDialog.getName(), FileTemplateManager.getInstance().getDefaultProperties(project), project, project.getBaseDir().findChild("Scenarios"))};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scenarioDialog = null;
        return PsiElement.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    protected PsiElement[] create(String newName, PsiDirectory directory) throws Exception {
        return new PsiElement[0];
    }

    @Override
    protected String getErrorTitle() {
        return null;
    }

    @Override
    protected String getCommandName() {
        return null;
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName) {
        return null;
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        //action will be visible only for PerfCake modules
        if (super.isAvailable(dataContext)) {
            Module module = LangDataKeys.MODULE.getData(dataContext);
            ModuleType type = ModuleType.get(module);
            return type instanceof PerfCakeModuleType;
        }
        return false;
    }
}
