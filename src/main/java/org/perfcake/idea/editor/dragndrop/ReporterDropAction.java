package org.perfcake.idea.editor.dragndrop;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

/**
 * Created by miron on 7. 12. 2014.
 */
public class ReporterDropAction implements DropAction {

    private Reporting reporting;

    public ReporterDropAction(Reporting reporting) {
        this.reporting = reporting;
    }

    @Override
    public boolean invoke(final String elementToAdd) {
        if (reporting.isValid()) {
            new WriteCommandAction(reporting.getModule().getProject(), "add " + elementToAdd, reporting.getXmlElement() == null ? null : reporting.getXmlElement().getContainingFile()) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    Reporter reporter = reporting.addReporter();
                    reporter.getClazz().setStringValue(elementToAdd);
                    reporter.getEnabled().setValue(true);
                }
            }.execute();
            return true;
        } else {
            return false;
        }
    }
}
