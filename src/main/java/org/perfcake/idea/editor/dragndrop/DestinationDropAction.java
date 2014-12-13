package org.perfcake.idea.editor.dragndrop;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.model.Destination;
import org.perfcake.idea.model.Reporter;

/**
 * Created by miron on 8. 12. 2014.
 */
public class DestinationDropAction implements DropAction {

    private Reporter reporter;

    public DestinationDropAction(Reporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public boolean invoke(final String elementToAdd) {
        if (reporter.isValid()) {
            new WriteCommandAction(reporter.getModule().getProject(), "add" + elementToAdd, reporter.getXmlElement().getContainingFile()) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    Destination destination = reporter.addDestination();
                    destination.getClazz().setStringValue(elementToAdd);
                    destination.getEnabled().setValue(true);
                }
            }.execute();
            return true;
        } else {
            return false;
        }
    }
}
