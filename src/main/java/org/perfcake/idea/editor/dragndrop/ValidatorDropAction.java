package org.perfcake.idea.editor.dragndrop;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.ValidatorDropDialog;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

/**
 * Created by miron on 8. 12. 2014.
 */
public class ValidatorDropAction implements DropAction {

    private Validation validation;

    public ValidatorDropAction(Validation validation) {
        this.validation = validation;
    }

    @Override
    public boolean invoke(final String elementToAdd) {
        if (validation.isValid()) {
            final Validation mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(validation);
            final Validator mockValidator = mockCopy.addValidator();

            final ValidatorDropDialog dialog = new ValidatorDropDialog(mockValidator);
            boolean ok = dialog.showAndGet();

            if (ok) {
                new WriteCommandAction(validation.getModule().getProject(), "add " + elementToAdd, validation.getXmlElement().getContainingFile()) {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        mockValidator.copyFrom(dialog.getMockCopy());
                        mockValidator.getClazz().setStringValue(elementToAdd);
                        validation.copyFrom(mockCopy);
                    }
                }.execute();
                return true;
            }
        }
        return false;
    }
}
