package org.perfcake.idea.run;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miron on 23.2.2014.
 */
public class PerfCakeRunner extends DefaultProgramRunner {

    @NotNull
    @Override
    public String getRunnerId() {
        return "PerfCakeRunner";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return executorId.equals(DefaultRunExecutor.EXECUTOR_ID) && profile instanceof PerfCakeRunConfiguration;
    }
}
