package org.perfcake.idea.run;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import org.apache.log4j.xml.DOMConfigurator;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * Created by miron on 7.4.2014.
 */
public class LoggerInitializer implements ModuleComponent {
    private static final Logger log = Logger.getInstance(ModuleComponent.class);
    private static final com.intellij.openapi.diagnostic.Logger ideaLogger = com.intellij.openapi.diagnostic.Logger.getInstance(ModuleComponent.class);

    public LoggerInitializer(Module module) {
    }

    public void initComponent() {

    }

    public void disposeComponent() {

    }

    @NotNull
    public String getComponentName() {
        return "LoggerInitializer";
    }

    public void projectOpened() {
        // called when project is opened
    }

    public void projectClosed() {
        // called when project is being closed
    }

    public void moduleAdded() {
        // Invoked when the module corresponding to this component instance has been completely
        // loaded and added to the project.

        // Initialize PerfCake library log4j configuration. This is needed in order to see logger output in console
        // when running PerfCake scenarios. Because log4j is using context classloader to load classes from configuration
        //we need to temporarily change context classloader to Pluginclassloader, that is loading PerfCake library.
        URL url = getClass().getResource("/log4j.xml");
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        DOMConfigurator.configure(url);
        Thread.currentThread().setContextClassLoader(contextClassLoader);


    }
}
