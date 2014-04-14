package org.perfcake.idea.module;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by miron on 21.1.2014.
 */
public class PerfCakeModuleBuilder extends JavaModuleBuilder {
    private String generator;
    private String sender;

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public PerfCakeModuleBuilder() {
    }

    public ModuleType getModuleType() {
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

        StartupManager.getInstance(rootModel.getProject()).runWhenProjectIsInitialized(new DumbAwareRunnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationManager.getApplication().runWriteAction(new Runnable() {
                            @Override
                            public void run() {
                                createStructure(root);
                            }
                        });
                    }
                });
            }
        });

    }

    private void createStructure(VirtualFile root) {
        try {
            VirtualFile scenariosDir = root.createChildDirectory(this, "Scenarios");
            VirtualFile messagesDir = root.createChildDirectory(this, "Messages");
        } catch (Exception ignore) {
        }
    }


}