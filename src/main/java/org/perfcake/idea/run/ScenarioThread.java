package org.perfcake.idea.run;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.perfcake.ScenarioExecution;
import org.perfcake.idea.util.Constants;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Thread that runs PerfCake scenario using PerfCake API
 * Created by miron on 9.3.2014.
 */
public class ScenarioThread implements Runnable {
    private static final Logger log = Logger.getInstance(ScenarioThread.class);
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


        String[] perfCakeArgs = createArgs();

        //runs the scenario
        ScenarioExecution.main(perfCakeArgs);


        //send message to console thread, that it can stop
        System.out.println(Constants.SCENARIO_FINISHED_MARK);

        System.setOut(systemOut);
        System.setErr(errOut);

        scenarioOutput.close();
        scenarioErrOutput.close();
    }

    /**
     * Creates arguments for PerfCake to run the scenario
     * @return
     */
    private String[] createArgs() {
        String scenarioPath = runConfiguration.getScenarioPath();
        String scenarioName = scenarioPath.substring(scenarioPath.lastIndexOf("/") + 1, scenarioPath.length() - 4);

        String[] args = new String[6];
        args[0] = "-s";
        args[1] = scenarioName;
        args[2] = "-sd";
        args[3] = runConfiguration.getScenariosDirPath();
        args[4] = "-md";
        args[5] = runConfiguration.getMessagesDirPath();

        return args;
    }
}
