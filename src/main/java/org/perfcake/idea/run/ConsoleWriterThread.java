package org.perfcake.idea.run;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.diagnostic.Logger;
import org.perfcake.idea.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by miron on 9.3.2014.
 */
public class ConsoleWriterThread implements Runnable {
    private final static Logger log = Logger.getInstance(ConsoleWriterThread.class);

    private ConsoleView console;
    private BufferedReader scenarioOutput;
    private BufferedReader scenarioErrOutput;

    public ConsoleWriterThread(ConsoleView console, InputStream scenarioOutput, InputStream scenarioErrOutput) {
        this.console = console;
        this.scenarioOutput = new BufferedReader(new InputStreamReader(scenarioOutput));
        this.scenarioErrOutput = new BufferedReader(new InputStreamReader(scenarioErrOutput));
    }

    @Override
    public void run() {
        try {
            boolean notFinished = true;
            while (notFinished) {
                while (scenarioErrOutput.ready()) {
                    console.print(scenarioErrOutput.readLine() + '\n', ConsoleViewContentType.ERROR_OUTPUT);
                }

                while (scenarioOutput.ready()) {
                    String s = scenarioOutput.readLine();
                    if (s.equals(Constants.SCENARIO_FINISHED_MARK)) {
                        notFinished = false;
                    } else {
                        console.print(s + '\n', ConsoleViewContentType.NORMAL_OUTPUT);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                scenarioOutput.close();
            } catch (IOException e) {
                log.error(e);
            }
            try {
                scenarioErrOutput.close();
            } catch (IOException e) {
                log.error(e);
            }

        }
    }
}
