package org.perfcake.idea.run;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeConst;
import org.perfcake.PerfCakeException;
import org.perfcake.idea.util.Constants;
import org.perfcake.scenario.Scenario;
import org.perfcake.scenario.ScenarioLoader;

import javax.xml.bind.JAXBException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Thread that runs PerfCake scenario using PerfCake API
 * Created by miron on 9.3.2014.
 */
public class ScenarioThread implements Runnable {
    private static final Logger LOG = Logger.getInstance(ScenarioThread.class);
    Scenario scenario = null;
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
        PrintStream systemOut = System.out;
        System.setOut(scenarioOutput);

        //redirect System error (perfcake) to scenarioErrOutput
        PrintStream errOut = System.err;
        System.setErr(scenarioErrOutput);

        setPerfCakeProperties();

        //run scenario
        try {
            //fix UnknownHostException for Windows
            String path = runConfiguration.getScenarioPath().startsWith("C:") ?
                    runConfiguration.getScenarioPath().substring(2) : runConfiguration.getScenarioPath();
            scenario = ScenarioLoader.load(path);
            scenario.init();
            scenario.run();
        } catch (Exception e) {
            LOG.info("Run error", e);
            System.err.println("Run Error: " + e);
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof JAXBException) {
                System.err.println("JAXB problem detected: " + cause);
            } else {
                System.err.println("Unknown cause. Showing stacktrace for cause detection:\n");
                e.printStackTrace();
            }

        } finally {
            if (scenario != null) try {
                scenario.close();
            } catch (PerfCakeException e) {
                System.err.println("Scenario close error: " + e.getMessage() + ". Cause: " + (e.getCause() == null ? "" : e.getCause().getMessage()) + "\nStackTrace:");
                e.printStackTrace();
            } finally {
                scenario = null;
            }
        }


        //send message to console thread, that it can stop
        System.out.println(Constants.SCENARIO_FINISHED_MARK);

        System.setOut(systemOut);
        System.setErr(errOut);

        scenarioOutput.close();
        scenarioErrOutput.close();
    }

    /**
     * Creates arguments for PerfCake to run the scenario
     */
    private void setPerfCakeProperties() {
        //fix UnknownHostException for Windows
        String messagesPath = runConfiguration.getMessagesDirPath().startsWith("C:") ?
                runConfiguration.getMessagesDirPath().substring(2) : runConfiguration.getMessagesDirPath();
        System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, messagesPath);

        String scenariosPath = runConfiguration.getScenariosDirPath().startsWith("C:") ?
                runConfiguration.getScenariosDirPath().substring(2) : runConfiguration.getScenariosDirPath();
        System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenariosPath);
    }

    public void stop() {
        if (scenario != null) {
            System.out.println("Stopping scenario...");
            scenario.stop();
        }
    }
}
