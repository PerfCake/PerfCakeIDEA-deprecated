package org.perfcake.idea.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by miron on 9.3.2014.
 */
public class PerfCakeRunProfileState implements RunProfileState {
    private static final Logger log = Logger.getLogger(PerfCakeRunProfileState.class);

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

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(console.getComponent(), "PerfCake Run", false);
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

                (new ScenarioThread(runConfiguration, pipeOut, pipeErrOut)).start();
                (new ConsoleWriterThread(console, pipeIn, pipeErrIn)).start();
            }
        });
        return null;
    }
}
