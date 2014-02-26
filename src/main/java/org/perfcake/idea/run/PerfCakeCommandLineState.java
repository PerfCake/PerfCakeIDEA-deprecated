package org.perfcake.idea.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;
import org.perfcake.PerfCakeException;
import org.perfcake.Scenario;
import org.perfcake.ScenarioBuilder;
import org.perfcake.parser.ScenarioParser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miron on 23.2.2014.
 */
public class PerfCakeCommandLineState extends CommandLineState {
    PerfCakeRunConfiguration runConfiguration;

    public PerfCakeCommandLineState(ExecutionEnvironment environment, PerfCakeRunConfiguration runConfiguration) {
        super(environment);
        this.runConfiguration = runConfiguration;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        //get an URL of a Scenario file
        URL scenarioURL;
        try {
            scenarioURL = new File(runConfiguration.getScenarioName()).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new ExecutionException("Error while constructing URL from scenario to run path", e);
        }

        //load Scenario XML to JAXB class
        org.perfcake.model.Scenario scenarioModel;
        try {
            scenarioModel = (new ScenarioParser(scenarioURL)).parse();
        } catch (PerfCakeException e) {
            throw new ExecutionException("Error while parsing scenario to run", e);
        }

        //get scenario class to execute
        Scenario scenario;
        try {
            scenario = (new ScenarioBuilder()).load(scenarioModel).build();
        } catch (Exception e) {
            throw new ExecutionException("Cannot load scenario to run", e);
        }

        /*//create process for ProcessHandler
        Process process = new Process() {
            @Override
            public OutputStream getOutputStream() {
                //return null;
                return new BufferedOutputStream(System.out);
            }

            @Override
            public InputStream getInputStream() {
                //return null;//new BufferedInputStream(System.out);
                return new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                };
            }

            @Override
            public InputStream getErrorStream() {
                //return null;//new BufferedInputStream(System.err);
                return new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                };
            }

            @Override
            public int waitFor() throws InterruptedException {
                //caka na ukoncenie tohto procesu,nevrati kym nie je ukonceny, ked ukonceny, vrati 0
                return 0;
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public void destroy() {

            }
        };*/


        //run the scenario
        PerfCakeProcessHandler processHandler = new PerfCakeProcessHandler();
        try {
            scenario.init();
            scenario.run();
        } catch (PerfCakeException e) {
            throw new ExecutionException("Error running scenario", e);
        } finally {
            try {
                scenario.close();
            } catch (PerfCakeException e) {
                throw new ExecutionException("Scenario did not finish properly", e);
            }
        }

        /*OSProcessHandler processHandler = new OSProcessHandler(process);
        processHandler.startNotify();
        return processHandler;*/
        return processHandler;

    }
}
