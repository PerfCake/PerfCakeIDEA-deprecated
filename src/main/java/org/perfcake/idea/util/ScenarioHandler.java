package org.perfcake.idea.util;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.PerfCakeException;
import org.perfcake.ScenarioBuilder;
import org.perfcake.model.Scenario;
import org.perfcake.parser.ScenarioParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

/**
 * Created by miron on 1.3.2014.
 */
public class ScenarioHandler {
    private static final Logger log = Logger.getInstance(ScenarioHandler.class);

    private URL scenarioURL;
    private Scenario scenarioModel;

    public Scenario getScenarioModel() {
        return scenarioModel;
    }

    /**
     * Creates new ScenarioHandler with scenario on a given scenarioPath and loads the scenario into model
     *
     * @param scenarioPath absolute path of valid scenario
     * @throws IllegalArgumentException if the scenarioPath cannot be resolved
     * @throws PerfCakeException        if scenario XML is not valid or cannot be successfully parsed
     */
    public ScenarioHandler(@NotNull String scenarioPath) throws PerfCakeException {
        //get an URL of a Scenario file
        try {
            scenarioURL = new File(scenarioPath).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Scenario path cannot be resolved", e);
        }
        //load Scenario XML to JAXB class
        scenarioModel = (new ScenarioParser(scenarioURL)).parse();
    }

    /**
     * Creates new ScenarioHandler with scenario on a given scenarioURL and loads the scenario into model
     *
     * @param scenarioURL
     * @throws PerfCakeException if scenario XML is not valid or cannot be successfully parsed
     */
    public ScenarioHandler(@NotNull URL scenarioURL) throws PerfCakeException {
        this.scenarioURL = scenarioURL;
        //load Scenario XML to JAXB class
        scenarioModel = (new ScenarioParser(scenarioURL)).parse();
    }

    /**
     * Creates template scenario on a given scenarioPath and returns its ScenarioHandler
     *
     * @param scenarioPath new scenario path
     * @param overwrite    overwrites scenario if already exists
     * @return ScenarioHandler with a loaded template scenario stored on scenarioPath
     * @throws FileAlreadyExistsException if the target scenarioPath already exists and overwrite is false
     * @throws PerfCakeIDEAException      if an error occures during template creation
     */
    public static ScenarioHandler createFromTemplate(@NotNull String scenarioPath, boolean overwrite) throws FileAlreadyExistsException, PerfCakeIDEAException {
        Path scenarioTarget = Paths.get(scenarioPath);
        URL templateURL = ScenarioHandler.class.getResource("/ScenarioTemplate.xml");

        try (InputStream in = templateURL.openStream()) {
            if (overwrite) {
                Files.copy(in, scenarioTarget, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(in, scenarioTarget);
            }
            return new ScenarioHandler(scenarioPath);
        } catch (IOException | PerfCakeException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw (FileAlreadyExistsException) e;
            }
            throw new PerfCakeIDEAException("Error while creating scenario from template", e);
        }
    }

    /**
     * Builds scenario for running from scenario model
     *
     * @return scenario to run
     * @throws PerfCakeIDEAException if scenario cannot be build
     */
    public org.perfcake.Scenario buildScenario() throws PerfCakeIDEAException {
        try {
            return (new ScenarioBuilder()).load(scenarioModel).build();
        } catch (Exception e) {
            throw new PerfCakeIDEAException("Cannot build scenario model", e);
        }
    }

    /**
     * Save current scenario model
     */
    public void save() {
        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            final Marshaller marshaller = context.createMarshaller();
            //marshaller.setSchema(); ?
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    try {
                        marshaller.marshal(scenarioModel, new File(scenarioURL.toURI()));
                    } catch (JAXBException | URISyntaxException e) {
                        log.error("Error saving scenario", e);
                        PerfCakeIdeaUtil.showError(getFocusedProject(), "Error saving scenario", e);
                    }
                }
            });
        } catch (JAXBException e) {
            log.error("Error saving scenario", e);
            PerfCakeIdeaUtil.showError(getFocusedProject(), "Error saving scenario", e);
        }
    }

    @Nullable
    private Project getFocusedProject() {
        DataManager dataManager = DataManager.getInstance();
        if (dataManager != null) {
            DataContext dataContext = dataManager.getDataContextFromFocus().getResult();
            return DataKeys.PROJECT.getData(dataContext);
        }
        return null;
    }
}
