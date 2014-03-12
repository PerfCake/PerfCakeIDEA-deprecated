package org.perfcake.idea.util;

import org.apache.log4j.Logger;
import org.perfcake.PerfCakeException;
import org.perfcake.ScenarioBuilder;
import org.perfcake.model.Scenario;
import org.perfcake.parser.ScenarioParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by miron on 1.3.2014.
 */
public class ScenarioHandler {
    private static final Logger log = Logger.getLogger(ScenarioHandler.class.getName());

    URL scenarioURL;
    Scenario scenarioModel;

    public Scenario getScenarioModel() {
        return scenarioModel;
    }

    public ScenarioHandler(String scenarioPath) throws MalformedURLException, PerfCakeException {
        //get an URL of a Scenario file
        scenarioURL = new File(scenarioPath).toURI().toURL();
        //load Scenario XML to JAXB class
        scenarioModel = (new ScenarioParser(scenarioURL)).parse();
    }

    /**
     * creates handler with teplate scenario
     * scenarioURL path to save scenario using save() method
     */
    public ScenarioHandler(URL scenarioURL) throws PerfCakeException {
        URL templateURL = this.getClass().getClassLoader().getResource("/ScenarioTemplate.xml");
        scenarioModel = (new ScenarioParser(templateURL)).parse();

        this.scenarioURL = scenarioURL;
    }

    /**
     * builds scenario to run from scenario model
     *
     * @return scenario to run
     * @throws Exception on error while building
     */
    public org.perfcake.Scenario buildScenario() throws Exception {
        return (new ScenarioBuilder()).load(scenarioModel).build();
    }

    public void save() {
        try {
            JAXBContext context = JAXBContext.newInstance(Scenario.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(scenarioModel, new File(scenarioURL.toURI()));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
