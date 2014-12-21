package org.perfcake.idea.wizard;

import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.module.PerfCakeModuleType;
import org.perfcake.idea.util.Constants;
import org.perfcake.idea.util.PerfCakeIDEAException;
import org.perfcake.idea.util.PerfCakeIdeaUtil;
import org.perfcake.idea.util.ScenarioUtil;

import java.util.Map;

/**
 * This class handles creation of a new Scenario from menu
 * Created by miron on 10.3.2014.
 */
public class NewScenarioAction extends CreateElementActionBase {
    private static final Logger log = Logger.getInstance(NewScenarioAction.class);

    public NewScenarioAction() {
        super("Scenario", "Create new PerfCake scenario", Constants.ICON_16P);
    }

    private String getNameWithExtension(String name) {
        if (name.length() > 4 && name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".xml")) {
            //xml extension already present
            return name;
        }
        return name + ".xml";
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {

        XmlFile scenario = ScenarioUtil.getTemplateModel(directory.getProject());
        Scenario scenarioDom = (Scenario) DomManager.getDomManager(project).getDomElement(scenario.getRootTag());
        NewScenarioWizard newScenarioWizard = new NewScenarioWizard(project, scenarioDom);
        boolean ok = newScenarioWizard.showAndGet();

        if (ok) {
            scenario.setName(getNameWithExtension(newScenarioWizard.nameStep.getScenarioName()));
            //find correct scenarios dir for new scenario
            Map<String, VirtualFile> moduleDirs = null;
            try {
                moduleDirs = PerfCakeIdeaUtil.resolveModuleDirsForFile(project, directory.getVirtualFile());
            } catch (PerfCakeIDEAException e) {
                log.error("Error creating template scenario", e);
                return PsiElement.EMPTY_ARRAY;
            }
            final VirtualFile scenariosDir = moduleDirs.get(PerfCakeConst.SCENARIOS_DIR_PROPERTY);
            PsiDirectory scenariosDirPsi = PsiManager.getInstance(project).findDirectory(scenariosDir);

            PsiElement created = ScenarioUtil.saveScenarioPsiToDir(scenariosDirPsi, scenario);
            return new PsiElement[]{created};
        }


        //Dialog closed without OK button
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
            if (module == null) return false;
            ModuleType type = ModuleType.get(module);
            return type instanceof PerfCakeModuleType;
        }
        return false;
    }
}
