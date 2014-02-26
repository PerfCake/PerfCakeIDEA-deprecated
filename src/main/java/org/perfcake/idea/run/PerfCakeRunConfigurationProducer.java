package org.perfcake.idea.run;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;

/**
 * Created by miron on 19.2.2014.
 */
public class PerfCakeRunConfigurationProducer extends RunConfigurationProducer<PerfCakeRunConfiguration> {

    public PerfCakeRunConfigurationProducer() {
        super(PerfCakeConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(PerfCakeRunConfiguration configuration, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        VirtualFile file = getFileFromContext(context);
        if (file == null) return false;

        //TODO Scenario filetype bind, or another way
        if (file.getFileType().getName().equals(StdFileTypes.XML.getName())) {
            configuration.setScenarioName(FileUtil.toSystemIndependentName(file.getPath()));
            return true;
        }

        return false;
    }

    @Override
    public boolean isConfigurationFromContext(PerfCakeRunConfiguration configuration, ConfigurationContext context) {
        VirtualFile file = getFileFromContext(context);
        if (file == null) return false;

        return FileUtil.toSystemIndependentName(file.getPath()).equals(configuration.getScenarioName());
    }

    private VirtualFile getFileFromContext(ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) return null;

        return location.getVirtualFile();
    }
}

