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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by miron on 10.6.2014.
 */
public class ScenarioUtil {

    private static final Logger LOG = Logger.getInstance(ScenarioUtil.class);


    /**
     * Gets template scenario XMLFile
     * @param project Intellij project
     * @param name name of the new XMLFile
     * @return new XML File
     */
    public static XmlFile getTemplateModel(Project project, String name){
        URL templateUrl = ScenarioUtil.class.getResource("/ScenarioTemplate.xml");
        String templateString = "";
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(templateUrl.toURI()));
            templateString = (new String(encoded, StandardCharsets.UTF_8)).replaceAll("\r", "");
        } catch (IOException|URISyntaxException e) {
            LOG.error("Error loading template scenario", e);
            throw new RuntimeException("Error loading template scenario", e);
        }
        return (XmlFile) PsiFileFactory.getInstance(project).createFileFromText(name, XmlFileType.INSTANCE, templateString);
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
