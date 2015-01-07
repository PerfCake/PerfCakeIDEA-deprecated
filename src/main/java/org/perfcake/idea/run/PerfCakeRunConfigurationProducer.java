package org.perfcake.idea.run;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import org.perfcake.idea.model.Scenario;

/**
 * Created by miron on 19.2.2014.
 * @see super
 */
public class PerfCakeRunConfigurationProducer extends RunConfigurationProducer<PerfCakeRunConfiguration> {

    private static final Logger LOG = Logger.getInstance(PerfCakeRunConfigurationProducer.class);

    public PerfCakeRunConfigurationProducer() {
        super(PerfCakeConfigurationType.getInstance());
    }

    /**
     * @see super
     */
    @Override
    protected boolean setupConfigurationFromContext(PerfCakeRunConfiguration configuration, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        VirtualFile file = getFileFromContext(context);
        if (file == null || !file.getFileType().getName().equals(StdFileTypes.XML.getName())) {
            return false;
        }

        PsiManager psiManager = PsiManager.getInstance(context.getProject());
        PsiFile psiFile = psiManager.findFile(file);
        if (psiFile == null) {
            return false;
        }
        DomManager manager = DomManager.getDomManager(context.getProject());
        if (manager.getFileElement((XmlFile) psiFile, Scenario.class) == null) {
            return false;
        }

        configuration.setScenarioPath(file.getPath());
        configuration.setGeneratedName();
        try {
            configuration.checkConfiguration();
        } catch (RuntimeConfigurationException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    /**
     * @see super
     */
    @Override
    public boolean isConfigurationFromContext(PerfCakeRunConfiguration configuration, ConfigurationContext context) {
        VirtualFile file = getFileFromContext(context);
        if (file == null) return false;

        return (file.getPath()).equals(configuration.getScenarioPath());
    }

    /**
     * Gets file for perfcake configuration
     * @param context
     * @return file or null if there is no file in the context
     */
    private VirtualFile getFileFromContext(ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) return null;

        return location.getVirtualFile();
    }
}

