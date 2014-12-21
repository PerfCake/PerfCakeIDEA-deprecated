package org.perfcake.idea.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by miron on 9.3.2014.
 */
public class PerfCakeRunProfileState implements RunProfileState {
    private static final Logger log = Logger.getInstance(PerfCakeRunProfileState.class);

    private final PerfCakeRunConfiguration runConfiguration;

    public PerfCakeRunProfileState(PerfCakeRunConfiguration runConfiguration) {
        this.runConfiguration = runConfiguration;
    }

    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {


        //create run console and attach it to tool window
        final ConsoleView console = TextConsoleBuilderFactory.getInstance().createBuilder(runConfiguration.getProject()).getConsole();
        ToolWindow toolWindow = ToolWindowManager.getInstance(runConfiguration.getProject()).getToolWindow(ToolWindowId.RUN);

        //wrap console content in order to use toolbar (for stop button)
        final ConsoleContentWrapper consoleContent = new ConsoleContentWrapper(console.getComponent());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(consoleContent, "PerfCake Run", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.getContentManager().setSelectedContent(content);

        //show console and run scenario
        toolWindow.show(new Runnable() {
            @Override
            public void run() {
                PipedOutputStream pipeOut = new PipedOutputStream();
                PipedInputStream pipeIn = null;

                PipedOutputStream pipeErrOut = new PipedOutputStream();
                PipedInputStream pipeErrIn = null;
                try {
                    pipeIn = new PipedInputStream(pipeOut);
                } catch (IOException e) {
                    log.error("Error connecting pipes", e);
                    return;
                }
                try {
                    pipeErrIn = new PipedInputStream(pipeErrOut);
                } catch (IOException e) {
                    log.error("Error connecting pipes", e);
                    return;
                }


                final ScenarioThread run = new ScenarioThread(runConfiguration, pipeOut, pipeErrOut);
                final Thread scenarioThread = new Thread(run);
                scenarioThread.setContextClassLoader(getClass().getClassLoader());

                //set run stop action
                consoleContent.getToolbar().getStopButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        run.stop();
                    }
                });
                scenarioThread.start();

                Thread consoleWriterThread = new Thread(new ConsoleWriterThread(console, pipeIn, pipeErrIn));
                consoleWriterThread.start();
            }
        });
        return null;
    }
}
