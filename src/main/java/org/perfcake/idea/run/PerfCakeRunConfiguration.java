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

    //parameters for persisting of configuration after Idea close
    private final String SCENARIO_PATH = "SCENARIO_PATH";
    private final String SCENARIOS_DIR_PATH = "SCENARIOS_DIR_PATH";
    private final String MESSAGES_DIR_PATH = "MESSAGES_DIR_PATH";

    private String scenarioPath;

    private String scenariosDirPath;
    private String messagesDirPath;

    public String getScenariosDirPath() {
        return scenariosDirPath;
    }

    private void setScenariosDirPath(String scenariosDirPath) {
        this.scenariosDirPath = scenariosDirPath;
    }

    public String getMessagesDirPath() {
        return messagesDirPath;
    }

    private void setMessagesDirPath(String messagesDirPath) {
        this.messagesDirPath = messagesDirPath;
    }

    @Override
    public String getScenarioPath() {
        return scenarioPath;
    }

    /**
     * Sets scenario file path and configures according to this path PerfCake run parameters
     * @param path scenario file path
     */
    @Override
    public void setScenarioPath(String path) {
        this.scenarioPath = path;
        setPerfCakeConfiguration(path);
    }

    /**
     * Resolves PerfCake dirs according to scenario (Module) location
     * @param scenarioPath scenario file path
     */
    private void setPerfCakeConfiguration(String scenarioPath) {
        File myScenario = new File(scenarioPath);
        VirtualFile scenario = LocalFileSystem.getInstance().findFileByIoFile(myScenario);
        if(myScenario.getPath().isEmpty() || scenario == null){
            //Idea continuos configuration commit, we dont want to set anything
            return;
        }
        Map<String, VirtualFile> moduleDirs;
        try{
            moduleDirs = PerfCakeIdeaUtil.resolveModuleDirsForFile(getProject(), scenario);
        }catch (PerfCakeIDEAException e){
            LOG.error("Could not set PerfCake run properties for scenario: " + scenarioPath, e);
            return;
        }

        String scenariosDir = moduleDirs.get(PerfCakeConst.SCENARIOS_DIR_PROPERTY).getPath();
        String messagesDir = moduleDirs.get(PerfCakeConst.MESSAGES_DIR_PROPERTY).getPath();

        setScenariosDirPath(scenariosDir);
        setMessagesDirPath(messagesDir);
    }



    protected PerfCakeRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    /**
     * Gets GUI Editor for PerfCake configuration
     * @return
     */
    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new PerfCakeRunConfigurationEditor(this);
    }

    /**
     * Gets PerfCakeIDEA run state which runs the scenario in console
     * @param executor
     * @param env
     * @return
     * @throws ExecutionException
     */
    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        return new PerfCakeRunProfileState(this);
    }


    public static void copyParams(PerfCakeRunConfigurationParams source, PerfCakeRunConfigurationParams target) {
        target.setScenarioPath(source.getScenarioPath());
    }

    /**
     * Checks validity of this configuration
     * @throws RuntimeConfigurationException on invalid configuration
     */
    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if (scenarioPath == null) {
            throw new RuntimeConfigurationError("Scenario not specified!");
        }
        if(scenariosDirPath == null || messagesDirPath == null){
            throw new RuntimeConfigurationError("PerfCake run properties was not properly set!");
        }
    }

    @Override
    public String suggestedName() {
        if (scenarioPath != null) {
            String name = (new File(scenarioPath)).getName();
        }
        return null;
    }



    @Override
    public void readExternal(Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);

        scenarioPath = JDOMExternalizerUtil.readField(element, SCENARIO_PATH);
        scenariosDirPath = JDOMExternalizerUtil.readField(element, SCENARIOS_DIR_PATH);
        messagesDirPath = JDOMExternalizerUtil.readField(element, MESSAGES_DIR_PATH);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);

        JDOMExternalizerUtil.writeField(element, SCENARIO_PATH, scenarioPath);
        JDOMExternalizerUtil.writeField(element, SCENARIOS_DIR_PATH, scenariosDirPath);
        JDOMExternalizerUtil.writeField(element, MESSAGES_DIR_PATH, messagesDirPath);

        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
    }

    //refactoring support, @see super
    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        if (element instanceof PsiFile) {
            VirtualFile virtualFile = ((PsiFile) element).getVirtualFile();
            try {
                if (virtualFile != null && (new File(virtualFile.getPath())).getCanonicalPath().equals((new File(scenarioPath)).getCanonicalPath())) {
                    return new RefactoringElementAdapter() {
                        @Override
                        protected void elementRenamedOrMoved(@NotNull PsiElement newElement) {
                            VirtualFile newFile = ((PsiFile) newElement).getVirtualFile();
                            if (newFile != null) {
                                setScenarioPath(FileUtil.toSystemIndependentName(newFile.getPath()));
                            }
                        }

                        @Override
                        public void undoElementMovedOrRenamed(@NotNull PsiElement newElement, @NotNull String oldQualifiedName) {
                            setScenarioPath(FileUtil.toSystemIndependentName(oldQualifiedName));
                        }
                    };
                }
            } catch (IOException ignore) {//just return null
            }
        }
        return null;
    }
}
