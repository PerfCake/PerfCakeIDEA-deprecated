package org.perfcake.idea.editor.dragndrop;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.PropertyDialog;
import org.perfcake.idea.model.IProperties;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

/**
 * Created by miron on 8. 12. 2014.
 */
public class PropertyDropAction implements DropAction {

    private IProperties properties;

    public PropertyDropAction(IProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean invoke(String elementToAdd) {
        final IProperties mockCopy = (IProperties) PerfCakeIdeaUtil.runCreateMockCopy(properties);

        final Property newProperty = mockCopy.addProperty();
        final PropertyDialog editDialog = new PropertyDialog(newProperty);
        boolean ok = editDialog.showAndGet();
        if (ok) {
            new WriteCommandAction(newProperty.getModule().getProject(), "Add Property", newProperty.getXmlElement().getContainingFile()) {
                @Override
                protected void run(@NotNull Result result) throws Throwable {
                    newProperty.copyFrom(editDialog.getMockCopy());
                    properties.copyFrom(mockCopy);
                }
            }.execute();
            return true;
        }
        return false;
    }
}
