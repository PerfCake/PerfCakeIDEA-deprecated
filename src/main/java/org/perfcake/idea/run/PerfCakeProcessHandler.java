package org.perfcake.idea.run;

import com.intellij.execution.process.ProcessHandler;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

/**
 * Created by miron on 24.2.2014.
 */
public class PerfCakeProcessHandler extends ProcessHandler {
    @Override
    protected void destroyProcessImpl() {

    }

    @Override
    protected void detachProcessImpl() {

    }

    @Override
    public boolean detachIsDefault() {
        return false;
    }

    @Nullable
    @Override
    public OutputStream getProcessInput() {
        return System.out;
    }
}
