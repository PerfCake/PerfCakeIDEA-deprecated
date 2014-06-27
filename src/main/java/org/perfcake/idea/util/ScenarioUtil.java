package org.perfcake.idea.util;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.PerfCakeException;
import org.perfcake.ScenarioBuilder;
import org.perfcake.model.Scenario;
import org.perfcake.parser.ScenarioParser;
import org.perfcake.util.Utils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

/**
 * Created by miron on 10.6.2014.
 */
public class ScenarioUtil {

    private static final Logger LOG = Logger.getInstance(ScenarioHandler.class);


    /**
     * Parses XML file inn a given scenarioFile and returns PerfCake Scenario model
     * @param scenarioFile VirtualFile with valid scenario
     * @return parsed PerfCake Scenario model
     * @throws PerfCakeIDEAException if scenarioPath cannot be resolved or scenario XML is not valid or cannot be successfully parsed
     */
    public static Scenario parse(@NotNull VirtualFile scenarioFile) throws PerfCakeIDEAException {
        //get an URL of a Scenario file
        try {
            URL scenarioURL = new URL(scenarioFile.getUrl());
            return parse(scenarioURL);
        } catch (MalformedURLException e) {
            throw new PerfCakeIDEAException("Scenario URL cannot be resolved: " + scenarioFile.getUrl(), e);
        }

    }


    /**
     * Parses XML file on a given scenarioURL and returns PerfCake Scenario model
     * @param scenarioURL URL of valid scenario
     * @return parsed PerfCake Scenario model
     * @throws PerfCakeIDEAException if scenario XML is not valid or cannot be successfully parsed
     */
    public static Scenario parse(@NotNull URL scenarioURL) throws PerfCakeIDEAException {
        try {
            //load Scenario XML to JAXB class and return
            return (new ScenarioParser(scenarioURL)).parse();
        } catch (PerfCakeException e) {
            throw new PerfCakeIDEAException("XML is not valid or cannot be successfully parsed", e);
        }
    }


    /**
     * Parses String containing PerfCake Scenario and returns it's model.
     * @param scenarioString String containing PerfCake scenario
     * @return parsed PerfCake Scenario model
     * @throws PerfCakeIDEAException if scenarioString is not valid PerfCake Scenario or cannot be successfully parsed
     */
    public static Scenario parse(String scenarioString) throws PerfCakeIDEAException {

        String filteredScenario;
        try {
            filteredScenario = org.perfcake.util.Utils.filterProperties(scenarioString);
        } catch (IOException e) {
            throw new PerfCakeIDEAException("Cannot filter scenario properties: ", e);
        }

        try {
            Source scenarioXML = new StreamSource(new ByteArrayInputStream(filteredScenario.getBytes(Utils.getDefaultEncoding())));
            String schemaFileName = "perfcake-scenario-" + org.perfcake.Scenario.VERSION + ".xsd";

            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            URL schemaUrl = ScenarioUtil.class.getResource("/schemas/" + schemaFileName);
            if (schemaUrl != null) {
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                try {
                    Schema schema = schemaFactory.newSchema(schemaUrl);
                    unmarshaller.setSchema(schema);
                } catch (SAXException e) {
                    LOG.warn("Scenario schema is not valid. Scenario reparsing continues without schema validation", e);
                }
            } else {
                LOG.warn("Could not get scenario schema: " + schemaFileName + ".Scenario reparsing continues without schema validation");
            }
            return (Scenario) unmarshaller.unmarshal(scenarioXML);
        } catch (JAXBException e) {
            throw new PerfCakeIDEAException("Cannot parse scenario from String: ", e);
        } catch (UnsupportedEncodingException e) {
            throw new PerfCakeIDEAException("PerfCake set encoding is not supported: ", e);
        }
    }




    /**
     * Writes template scenario to a given scenarioPath
     *
     * @param scenarioPath new scenario path
     * @param overwrite    overwrites scenario if already exists
     * @throws FileAlreadyExistsException if the target scenarioPath already exists and overwrite is false
     * @throws PerfCakeIDEAException      if an error occures during template creation
     */
    public static void createFromTemplate(@NotNull String scenarioPath, boolean overwrite) throws FileAlreadyExistsException, IOException{
        Path scenarioTarget = Paths.get(scenarioPath);
        URL templateURL = ScenarioHandler.class.getResource("/ScenarioTemplate.xml");

        try (InputStream in = templateURL.openStream()) {
            if (overwrite) {
                Files.copy(in, scenarioTarget, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(in, scenarioTarget);
            }
        }
    }




    /**
     * Builds scenario for running from PerfCake scenario model
     *
     * @return scenario to run
     * @throws PerfCakeIDEAException if scenario cannot be build
     */
    public org.perfcake.Scenario buildScenario(Scenario scenarioModel) throws PerfCakeIDEAException {
        try {
            return (new ScenarioBuilder()).load(scenarioModel).build();
        } catch (Exception e) {
            throw new PerfCakeIDEAException("Cannot build scenario model", e);
        }
    }



    /**
     * Save scenario model. In case of failure, error message is shown to user.
     */
    public static void save(final Scenario scenarioModel, final URL pathToSaveTo) {
        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            final Marshaller marshaller = context.createMarshaller();

            String schemaFileName = "perfcake-scenario-" + org.perfcake.Scenario.VERSION + ".xsd";
            URL schemaUrl = ScenarioUtil.class.getResource("/schemas/" + schemaFileName);
            if (schemaUrl != null) {
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                try {
                    Schema schema = schemaFactory.newSchema(schemaUrl);
                    marshaller.setSchema(schema);
                } catch (SAXException e) {
                    LOG.warn("Scenario schema is not valid. Scenario saving continues without validation", e);
                }
            } else {
                LOG.warn("Could not get scenario schema: " + schemaFileName + ". Scenario saving continues without schema validation");
            }

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    try {
                        marshaller.marshal(scenarioModel, new File(pathToSaveTo.toURI()));
                    } catch (JAXBException | URISyntaxException e) {
                        LOG.error("Error saving scenario", e);
                        PerfCakeIdeaUtil.showError(getFocusedProject(), "Error saving scenario", e);
                    }
                }
            });
        } catch (JAXBException e) {
            LOG.error("Error saving scenario", e);
            PerfCakeIdeaUtil.showError(getFocusedProject(), "Error saving scenario", e);
        }
    }


    @Nullable
    private static Project getFocusedProject() {
        DataManager dataManager = DataManager.getInstance();
        if (dataManager != null) {
            DataContext dataContext = dataManager.getDataContextFromFocus().getResult();
            return DataKeys.PROJECT.getData(dataContext);
        }
        return null;
    }
}
