package org.perfcake.idea.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.listeners.RefactoringElementAdapter;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.PerfCakeConst;
import org.perfcake.idea.util.PerfCakeIDEAException;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by miron on 4.2.2014.
 */
public class PerfCakeRunConfiguration extends LocatableConfigurationBase implements PerfCakeRunConfigurationParams, RefactoringListenerProvider {
    private static final Logger LOG = Logger.getInstance(PerfCakeRunConfiguration.class);

    private final String SCENARIO_NAME = "SCENARIO_NAME";
    private String scenarioName;

    protected PerfCakeRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new PerfCakeRunConfigurationEditor(this);
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        return new PerfCakeRunProfileState(this);
    }

    @Override
    public String getScenarioName() {
        return scenarioName;
    }

    @Override
    public void setScenarioName(String name) {
        this.scenarioName = name;
        try {
            setPerfCakeRunProperties(name);
        } catch (PerfCakeIDEAException e) {
            LOG.error(e);
        }
    }

    /**
     * sets PerfCake System properties according to scenario which we want to run
     *
     * @param scenarioPath scenario to run
     * @throws PerfCakeIDEAException when we could not resolve properties for current module
     */
    private void setPerfCakeRunProperties(String scenarioPath) throws PerfCakeIDEAException {
        VirtualFile scenarioVFile = LocalFileSystem.getInstance().findFileByIoFile(new File(scenarioPath));
        Map<String, VirtualFile> moduleDirs = null;
        try {
            moduleDirs = PerfCakeIdeaUtil.resolveModuleDirsForFile(getProject(), scenarioVFile);
        } catch (PerfCakeIDEAException e) {
            throw new PerfCakeIDEAException("Could not set PerfCake run properties for scenario: " + scenarioPath, e);
        }

        String scenariosDir = moduleDirs.get(PerfCakeConst.SCENARIOS_DIR_PROPERTY).getPath();
        String messagesDir = moduleDirs.get(PerfCakeConst.MESSAGES_DIR_PROPERTY).getPath();

        System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, FileUtil.toSystemDependentName(scenariosDir));
        System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, FileUtil.toSystemDependentName(messagesDir));

    }

    public static void copyParams(PerfCakeRunConfigurationParams source, PerfCakeRunConfigurationParams target) {
        target.setScenarioName(source.getScenarioName());
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if (scenarioName == null) {
            throw new RuntimeConfigurationError("Scenario not specified!");
        }
    }

    @Override
    public String suggestedName() {
        if (scenarioName != null) {
            String name = (new File(scenarioName)).getName();
            if (name.length() > 4) return name.substring(0, name.length() - 4);
        }
        return null;
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);
        scenarioName = JDOMExternalizerUtil.readField(element, SCENARIO_NAME);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        JDOMExternalizerUtil.writeField(element, SCENARIO_NAME, scenarioName);
        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
    }

    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        if (element instanceof PsiFile) {
            VirtualFile virtualFile = ((PsiFile) element).getVirtualFile();
            try {
                if (virtualFile != null && (new File(virtualFile.getPath())).getCanonicalPath().equals((new File(scenarioName)).getCanonicalPath())) {
                    return new RefactoringElementAdapter() {
                        @Override
                        protected void elementRenamedOrMoved(@NotNull PsiElement newElement) {
                            VirtualFile newFile = ((PsiFile) newElement).getVirtualFile();
                            if (newFile != null) {
                                scenarioName = FileUtil.toSystemIndependentName(newFile.getPath());
                            }
                        }

                        @Override
                        public void undoElementMovedOrRenamed(@NotNull PsiElement newElement, @NotNull String oldQualifiedName) {
                            scenarioName = FileUtil.toSystemIndependentName(oldQualifiedName);
                        }
                    };
                }
            } catch (IOException ignore) {
            }
        }
        return null;
    }
}
