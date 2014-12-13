package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.ValidatorAddAction;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.ValidatorDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validation;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidationComponent extends BasicDomElementComponent<Validation> {

    private static final String TITLE = "Validation";

    private JPerfCakeIdeaRectangle validationGui;

    public ValidationComponent(Validation domElement) {
        super((Validation) domElement.createStableCopy());

        validationGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.VALIDATION_FOREGROUND, ColorType.VALIDATION_BACKGROUND);

        createSetActions();

        validationGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    validationGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        validationGui.addMouseListener(new PopClickListener(domElement, getComponent()));

        //set dropping from toolbar to this component
        validationGui.setTransferHandler(new ComponentTransferHandler("Validators", new ValidatorDropAction(domElement)));

        addValidators();
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        ValidatorAddAction addAction = new ValidatorAddAction(getDomElement(), validationGui);
        EditAction editAction = new EditAction("Edit Validation", getDomElement(), validationGui);

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);

        validationGui.setActionMap(actionMap);
    }


    @Override
    public JComponent getComponent() {
        return validationGui;
    }

    private void addValidators() {
        if (getDomElement().isValid()) {
            for (Validator v : myDomElement.getValidators()) {
                ValidatorComponent validatorComponent = new ValidatorComponent(v);
                addComponent(validatorComponent);
                validationGui.addComponent(validatorComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        validationGui.removeAllComponents();

        addValidators();
    }
}
