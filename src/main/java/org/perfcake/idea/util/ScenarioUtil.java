package org.perfcake.idea.util;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 * Created by miron on 10.6.2014.
 */
public class ScenarioUtil {

    private static final Logger LOG = Logger.getInstance(ScenarioUtil.class);


    /**
     * Gets template scenario XMLFile
     * @param project Intellij project
     * @return new template scenario
     */
    public static XmlFile getTemplateModel(Project project) {
        InputStream scenarioIs = ScenarioUtil.class.getResourceAsStream("/TemplateScenario.xml");
        java.util.Scanner s = new java.util.Scanner(scenarioIs).useDelimiter("\\A");
        String scenarioString;
        if (s.hasNext()) {
            scenarioString = s.next();
            scenarioString = scenarioString.replaceAll("\r", "");
        } else {
            LOG.error("Error loading template scenario");
            throw new RuntimeException("Error loading template scenario");
        }
        return (XmlFile) PsiFileFactory.getInstance(project).createFileFromText("template.xml", XmlFileType.INSTANCE, scenarioString);
    }

    /**
     * Saves scenario to specified directory and returns saved scenario
     * @param dir to which to save
     * @param scenario to save
     * @return saved scenario element
     */
    public static PsiElement saveScenarioPsiToDir(final PsiDirectory dir, final XmlFile scenario){
        return new WriteAction<PsiElement>() {
            @Override
            protected void run(@NotNull Result<PsiElement> result) throws Throwable {
                result.setResult(dir.add(scenario));
            }
        }.execute().getResultObject();
    }
}
