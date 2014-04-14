package org.perfcake.idea.module;

import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.Constants;
import org.perfcake.idea.util.PerfCakeIDEAException;
import org.perfcake.idea.util.PerfCakeIdeaUtil;
import org.perfcake.idea.util.ScenarioHandler;
import org.perfcake.model.Scenario;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

/**
 * Created by miron on 10.3.2014.
 */
public class NewScenarioAction extends CreateElementActionBase {
    private static final Logger log = Logger.getInstance(CreateElementActionBase.class);

    public NewScenarioAction() {
        super("", "", Constants.NODE_ICON);
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
        NewScenarioDialog scenarioDialog = new NewScenarioDialog(project);
        scenarioDialog.show();

        if (scenarioDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            //create scenario with specified values in module's Scenarios directory
            Map<String, VirtualFile> moduleDirs = null;
            try {
                moduleDirs = PerfCakeIdeaUtil.resolveModuleDirsForFile(project, directory.getVirtualFile());
            } catch (PerfCakeIDEAException e) {
                PerfCakeIdeaUtil.showError(project, "Error creating template scenario", e);
                return PsiElement.EMPTY_ARRAY;
            }
            final VirtualFile scenariosDir = moduleDirs.get(PerfCakeConst.SCENARIOS_DIR_PROPERTY);
            String scenarioName = scenarioDialog.getName().endsWith(".xml") ? scenarioDialog.getName() : scenarioDialog.getName() + ".xml";
            final String scenarioPath = FileUtil.toSystemDependentName(scenariosDir.getPath()) + File.separator + scenarioName;


            ScenarioHandler handler = null;
            try {
                handler = ScenarioHandler.createFromTemplate(scenarioPath, false);
            } catch (FileAlreadyExistsException e) {
                log.warn("Scenario with this name already exists", e);
                int result = Messages.showYesNoDialog(project, "Scenario with this name already exists. Do you want to overwrite existing file?", "Scenario exists", Messages.getWarningIcon());
                if (result == Messages.YES) {
                    try {
                        handler = ScenarioHandler.createFromTemplate(scenarioPath, true);
                    } catch (FileAlreadyExistsException ignored) {
                        //cannot happen, overwrite is true
                    } catch (PerfCakeIDEAException e1) {
                        PerfCakeIdeaUtil.showError(project, "Error creating template scenario", e);
                        return PsiElement.EMPTY_ARRAY;
                    }
                } else {
                    return PsiElement.EMPTY_ARRAY;
                }
            } catch (PerfCakeIDEAException e) {
                PerfCakeIdeaUtil.showError(project, "Error creating template scenario", e);
                return PsiElement.EMPTY_ARRAY;
            }
            //set chosen values and save scenario
            Scenario model = handler.getScenarioModel();
            model.getGenerator().setClazz(scenarioDialog.getGeneratorName());
            model.getSender().setClazz(scenarioDialog.getSenderName());
            handler.save();

            //refresh Scenarios directory to see new scenario
            scenariosDir.refresh(false, false);
            VirtualFile newScenario = scenariosDir.findChild(scenarioName);
            return new PsiElement[]{PsiManager.getInstance(project).findFile(newScenario)};

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
            ModuleType type = ModuleType.get(module);
            return type instanceof PerfCakeModuleType;
        }
        return false;
    }
}
