package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.gui.GeneratorGui;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.model.Generator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class GeneratorComponent extends BasicDomElementComponent<Generator> {

    private GeneratorGui generatorGui;

    public GeneratorComponent(Generator domElement) {
        super((Generator) domElement.createStableCopy());

        generatorGui = new GeneratorGui(getDomElement().getClazz().getStringValue(), getDomElement().getThreads().getStringValue());

        createSetActions();

        generatorGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    generatorGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        generatorGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        generatorGui.setTransferHandler(new ComponentTransferHandler("Properties", new PropertyDropAction(getDomElement())));

        RunComponent runComponent = new RunComponent(getDomElement().getRun());
        generatorGui.addComponent(runComponent.getComponent());
        addComponent(runComponent);
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        PropertyAddAction addAction = new PropertyAddAction(getDomElement(), generatorGui);
        EditAction editAction = new EditAction<Generator>("Edit Generator", getDomElement(), generatorGui);

        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.EDIT, editAction);

        generatorGui.setActionMap(actionMap);
    }

    @Override
    public JComponent getComponent() {
        return generatorGui;
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            generatorGui.setClazz(getDomElement().getClazz().getStringValue());
            generatorGui.setThreads(getDomElement().getThreads().getStringValue());
        }
    }

}
