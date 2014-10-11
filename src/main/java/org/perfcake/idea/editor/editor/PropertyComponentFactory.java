package org.perfcake.idea.editor.editor;

import com.intellij.openapi.util.Factory;
import org.perfcake.idea.editor.ui.PropertyComponent;
import org.perfcake.idea.model.Property;

/**
 * Created by miron on 10. 10. 2014.
 */
public class PropertyComponentFactory implements Factory<PropertyComponent> {

    private final Property p;

    public PropertyComponentFactory(Property p) {
        this.p = p;
    }

    @Override
    public PropertyComponent create() {
        return new PropertyComponent(p);
    }
}
