package org.perfcake.idea.module;

import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by miron on 21.1.2014.
 */
public class PerfCakeModuleBuilder extends JavaModuleBuilder {

    public ModuleType getModuleType(){
        return PerfCakeModuleType.getInstance();
    }

    @Override
    public String getGroupName() {
        return PerfCakeProjectTemplatesFactory.PERFCAKE;
    }

    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        super.setupRootModel(rootModel);

        VirtualFile[] roots = rootModel.getContentRoots();
        final VirtualFile root = roots[0];

        final Project project = rootModel.getProject();

        StartupManager.getInstance(rootModel.getProject()).runWhenProjectIsInitialized(new DumbAwareRunnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationManager.getApplication().runWriteAction(new Runnable() {
                            @Override
                            public void run() {
                                createStructure(root, project);
                            }
                        });
                    }
                });
            }
        });
    }

    private void createStructure(VirtualFile root, Project project){
        try {
            VirtualFile scenariosDir = root.createChildDirectory(this, "Scenarios");
            VirtualFile messagesDir = root.createChildDirectory(this, "Messages");
            PerfCakeFileTemplates.createFromTemplate(PerfCakeFileTemplates.SCENARIO, "Scenario", FileTemplateManager.getInstance().getDefaultProperties(project), project, scenariosDir);
        } catch (Exception ignore){
        }
    }

}