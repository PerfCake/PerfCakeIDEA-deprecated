package org.perfcake.idea.run;

import org.apache.log4j.Logger;
import org.perfcake.PerfCakeException;
import org.perfcake.Scenario;
import org.perfcake.idea.util.ScenarioHandler;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by miron on 9.3.2014.
 */
public class ScenarioThread extends Thread {
    private static final Logger log = Logger.getLogger(ScenarioThread.class);

    private PerfCakeRunConfiguration runConfiguration;
    private OutputStream scenarioOutput;

    public ScenarioThread(PerfCakeRunConfiguration runConfiguration, OutputStream scenarioOutput) {
        this.runConfiguration = runConfiguration;
        this.scenarioOutput = scenarioOutput;
    }

    @Override
    public void run() {
        //redirect perfcake output to scenarioOutput
        PrintStream systemOut = System.out;
        PrintStream scenarioOut = new PrintStream(scenarioOutput, true);
        System.setOut(scenarioOut);
        try {
            ScenarioHandler handler = new ScenarioHandler(runConfiguration.getScenarioName());
            Scenario scenario = handler.buildScenario();
            runScenario(scenario);
        } catch (Exception e) {
            //TODO show user the error
            log.error("Error running scenario file", e);
        }
        System.setOut(systemOut);
        scenarioOut.close();

    }

    private void runScenario(Scenario scenario) throws PerfCakeException {
        try {
            scenario.init();
            scenario.run();
        } finally {
            scenario.close();
        }
    }
}
