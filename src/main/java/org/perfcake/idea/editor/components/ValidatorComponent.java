package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.DeleteAction;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miron on 21.10.2014.
 */
public class ValidatorComponent extends BasicDomElementComponent<Validator> {

    private JPerfCakeIdeaRectangle validatorGui;

    public ValidatorComponent(Validator domElement) {
        super((Validator) domElement.createStableCopy());

        validatorGui = new JPerfCakeIdeaRectangle(getGuiText(), ColorType.VALIDATOR_FOREGROUND, ColorType.VALIDATOR_BACKGROUND);

        createSetActions();

        validatorGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    validatorGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }

        });
        validatorGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        validatorGui.setTransferHandler(new ComponentTransferHandler("Properties", new PropertyDropAction(getDomElement())));
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PropertyAddAction addAction = new PropertyAddAction(getDomElement(), validatorGui);
        EditAction editAction = new EditAction<Validator>("Edit Validator", getDomElement(), validatorGui);

        List<Validator> validatorList = new ArrayList<>();
        validatorList.add(getDomElement());
        DeleteAction deleteAction = new DeleteAction("Delete Validator", validatorList, validatorGui);

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);
        actionMap.put(ActionType.DELETE, deleteAction);

        validatorGui.setActionMap(actionMap);
    }

    @Override
    public JComponent getComponent() {
        return validatorGui;
    }

    private String getGuiText() {
        return "(" + getDomElement().getId().getStringValue() + ") " + getDomElement().getClazz().getStringValue();
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            validatorGui.setTitle(getGuiText());
        }
    }
}
