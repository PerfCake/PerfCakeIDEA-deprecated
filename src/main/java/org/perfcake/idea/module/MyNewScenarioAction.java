package org.perfcake.idea.module;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

/**
 * Created by miron on 23.10.2014.
 */
public class MyNewScenarioAction extends CreateFileFromTemplateAction {
    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {

    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return "New Scenario";
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        //action will be visible only for PerfCake modules
        if (super.isAvailable(dataContext)) {
            Module module = LangDataKeys.MODULE.getData(dataContext);
            if (module == null) return false;
            ModuleType type = ModuleType.get(module);
            return type instanceof PerfCakeModuleType;
        }
        return false;
    }
}
