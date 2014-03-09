package org.perfcake.idea.run;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by miron on 9.3.2014.
 */
public class ConsoleWriterThread extends Thread {
    private final static Logger log = Logger.getLogger(ConsoleWriterThread.class);

    private ConsoleView console;
    private InputStream scenarioOutput;

    public ConsoleWriterThread(ConsoleView console, InputStream scenarioOutput) {
        this.console = console;
        this.scenarioOutput = scenarioOutput;
    }

    @Override
    public void run() {
        BufferedReader scenarioOut = new BufferedReader(new InputStreamReader(scenarioOutput));
        String s;
        try {
            while ((s = scenarioOut.readLine()) != null) {
                console.print(s + '\n', ConsoleViewContentType.NORMAL_OUTPUT);
            }
        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                scenarioOut.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
