package org.perfcake.idea.editor.ui;

import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.dialogs.PropertyEditDialog;
import org.perfcake.idea.editor.model.PropertyModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 29.4.2014.
 */
public class PropertyRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private PropertyModel model;

    public PropertyRectangle(@NotNull PropertyModel model) {
        super(model.toString());
        this.model = model;
        this.model.addPropertyChangeListener(this);
        //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @NotNull
    public PropertyModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        label.setText(model.toString());
    }

    public String toString() {
        return getClass().getName() + " " + model.toString();
    }

    public void invokeDialog() {
        PropertyEditDialog propertyEditDialog = new PropertyEditDialog(null, model.getProperty().getName(), model.getProperty().getValue());
        propertyEditDialog.show();
        if (propertyEditDialog.isOK()) {
            model.setName(propertyEditDialog.getNameText());
            model.setValue(propertyEditDialog.getValueText());
        }
    }
}
