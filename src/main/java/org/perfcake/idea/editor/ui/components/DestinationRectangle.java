package org.perfcake.idea.editor.ui.components;

import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.model.DestinationModel;
import org.perfcake.model.Scenario;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 15.5.2014.
 */
public class DestinationRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private DestinationModel model;

    public DestinationRectangle(DestinationModel model) {
        super(model.getDestination().getClazz());
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    public DestinationModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DestinationModel.CLAZZ_PROPERTY)) {
            label.setText(model.getDestination().getClazz());
        }
        if (evt.getPropertyName().equals(DestinationModel.ENABLED_PROPERTY)) {
            //TODO enabled
        }
        if(evt.getPropertyName().equals(DestinationModel.DESTINATION_PROPERTY)){
            updateRectangle();
        }
    }
    private void updateRectangle(){
        label.setText(model.getDestination().getClazz());
        //TODO enabled
    }
}
