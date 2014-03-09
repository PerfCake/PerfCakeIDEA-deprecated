package org.perfcake.idea.util;

import org.apache.log4j.Logger;
import org.perfcake.PerfCakeException;
import org.perfcake.ScenarioBuilder;
import org.perfcake.model.Scenario;
import org.perfcake.parser.ScenarioParser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miron on 1.3.2014.
 */
public class ScenarioHandler {
    private static final Logger log = Logger.getLogger(ScenarioHandler.class.getName());

    Scenario scenarioModel;

    public ScenarioHandler(String scenarioPath) throws MalformedURLException, PerfCakeException {
        //get an URL of a Scenario file
        URL scenarioURL = new File(scenarioPath).toURI().toURL();
        //load Scenario XML to JAXB class
        scenarioModel = (new ScenarioParser(scenarioURL)).parse();
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
}
