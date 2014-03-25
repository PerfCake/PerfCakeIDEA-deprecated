package org.perfcake.idea.run;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeException;
import org.perfcake.Scenario;
import org.perfcake.idea.Constants;
import org.perfcake.idea.util.ScenarioHandler;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by miron on 9.3.2014.
 */
public class ScenarioThread extends Thread {
    private static final Logger log = Logger.getLogger(ScenarioThread.class);

    private PerfCakeRunConfiguration runConfiguration;
    private PrintStream scenarioOutput;
    private PrintStream scenarioErrOutput;

    public ScenarioThread(@NotNull PerfCakeRunConfiguration runConfiguration, @NotNull OutputStream scenarioOutput, @NotNull OutputStream scenarioErrOutput) {
        this.runConfiguration = runConfiguration;
        this.scenarioOutput = new PrintStream(scenarioOutput, true);
        this.scenarioErrOutput = new PrintStream(scenarioErrOutput, true);
    }

    @Override
    public void run() {
        //redirect System output (perfcake) to scenarioOutput
        PrintStream systemOut = System.out;
        System.setOut(scenarioOutput);

        //redirect System error (perfcake) to scenarioErrOutput
        PrintStream errOut = System.err;
        System.setErr(scenarioErrOutput);

        try {
            ScenarioHandler handler = new ScenarioHandler(runConfiguration.getScenarioName());
            System.out.println("Running scenario " + runConfiguration.getScenarioName());
            Scenario scenario = handler.buildScenario();
            runScenario(scenario);
        } catch (Exception e) {
            log.error("Error running scenario file", e);
            System.err.println("Error running scenario file: " + e.getMessage());
        }
        //send message to console thread, that it can stop
        System.out.println(Constants.SCENARIO_FINISHED_MARK);

        System.setOut(systemOut);
        System.setErr(errOut);

        scenarioOutput.close();
        scenarioErrOutput.close();
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
