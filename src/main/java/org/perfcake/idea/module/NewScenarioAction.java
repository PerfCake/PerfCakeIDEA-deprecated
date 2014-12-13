package org.perfcake.idea.module;

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
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.model.Scenario;
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
    NewScenarioDialog scenarioDialog;

    public NewScenarioAction() {
        super("Scenario", "Create new PerfCake scenario", Constants.ICON_16P);
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {

        XmlFile scenario = ScenarioUtil.getTemplateModel(directory.getProject(), "");
        Scenario domElement = (Scenario) DomManager.getDomManager(project).getDomElement(scenario.getRootTag());
        NewScenarioWizard newScenarioWizard = new NewScenarioWizard(project, domElement);
        boolean ok = newScenarioWizard.showAndGet();

        if (ok) {
            scenario.setName(newScenarioWizard.nameStep.getScenarioName() + ".xml");
            //find correct scenarios dir for new scenario
            Map<String, VirtualFile> moduleDirs = null;
            try {
                moduleDirs = PerfCakeIdeaUtil.resolveModuleDirsForFile(project, directory.getVirtualFile());
            } catch (PerfCakeIDEAException e) {
                log.error("Error creating template scenario", e);
                scenarioDialog = null;
                return PsiElement.EMPTY_ARRAY;
            }
            final VirtualFile scenariosDir = moduleDirs.get(PerfCakeConst.SCENARIOS_DIR_PROPERTY);
            PsiDirectory scenariosDirPsi = PsiManager.getInstance(project).findDirectory(scenariosDir);

            //concat xml extension if not present
            //String scenarioName = scenarioDialog.getName().endsWith(".xml") ||  scenarioDialog.getName().endsWith(".XML") ?
            //        scenarioDialog.getName() : scenarioDialog.getName() + ".xml";

            //create new scenario file

            PsiElement created = ScenarioUtil.saveScenarioPsiToDir(scenariosDirPsi, scenario);
            return new PsiElement[]{created};

        }


        //Dialog closed without OK button
        scenarioDialog = null;
        return PsiElement.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    protected PsiElement[] create(String newName, PsiDirectory directory) throws Exception {
        XmlFile scenario = ScenarioUtil.getTemplateModel(directory.getProject(), newName);

        XmlTag root = scenario.getRootTag();
        XmlTag generator = root.findFirstSubTag("generator");
        XmlAttribute generatorClass = generator.getAttribute("class");
        generatorClass.setValue(scenarioDialog.getGeneratorName());
        XmlTag sender = root.findFirstSubTag("sender");
        XmlAttribute senderClass = sender.getAttribute("class");
        senderClass.setValue(scenarioDialog.getSenderName());

        PsiElement created = ScenarioUtil.saveScenarioPsiToDir(directory, scenario);

        scenarioDialog = null;
        return new PsiElement[]{created};
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
