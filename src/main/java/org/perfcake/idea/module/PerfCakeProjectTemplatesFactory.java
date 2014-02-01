package org.perfcake.idea.module;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.intellij.platform.templates.BuilderBasedTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miron on 28.1.2014.
 */
public class PerfCakeProjectTemplatesFactory extends ProjectTemplatesFactory {
    public static final String PERFCAKE = "PerfCake";
    @NotNull
    @Override
    public String[] getGroups() {
        return new String[]{PERFCAKE};
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(String group, WizardContext context) {

        return new ProjectTemplate[]{
                new BuilderBasedTemplate(new PerfCakeModuleBuilder()),
        };
    }

}
