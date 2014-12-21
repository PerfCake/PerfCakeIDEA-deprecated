package org.perfcake.idea.editor.components;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.actions.EditAction;
import org.perfcake.idea.editor.actions.PropertyAddAction;
import org.perfcake.idea.editor.actions.ReporterAddAction;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dragndrop.ComponentTransferHandler;
import org.perfcake.idea.editor.dragndrop.DropAction;
import org.perfcake.idea.editor.dragndrop.PropertyDropAction;
import org.perfcake.idea.editor.dragndrop.ReporterDropAction;
import org.perfcake.idea.editor.menu.ActionType;
import org.perfcake.idea.editor.menu.PopClickListener;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Reporter;
import org.perfcake.idea.model.Reporting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Created by miron on 21.10.2014.
 */
public class ReportingComponent extends BasicDomElementComponent<Reporting> {

    private static final String TITLE = "Reporting";

    private JPerfCakeIdeaRectangle reportingGui;

    public ReportingComponent(Reporting domElement) {
        super((Reporting) domElement.createStableCopy());

        reportingGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.REPORTING_FOREGROUND, ColorType.REPORTING_BACKGROUND);

        createSetActions();


        reportingGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    reportingGui.getActionMap().get(ActionType.EDIT).actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        reportingGui.addMouseListener(new PopClickListener(getDomElement(), getComponent()));

        //set dropping from toolbar to this component
        HashMap<String, DropAction> prefixDropActions = new HashMap<>();
        prefixDropActions.put("Reporters", new ReporterDropAction(domElement));
        prefixDropActions.put("Properties", new PropertyDropAction(domElement));
        reportingGui.setTransferHandler(new ComponentTransferHandler(prefixDropActions));


        addReporters();
    }

    private void createSetActions() {
        ActionMap actionMap = new ActionMap();

        ReporterAddAction addAction = new ReporterAddAction(getDomElement(), getComponent());
        PropertyAddAction addAction2 = new PropertyAddAction(getDomElement(), getComponent());

        EditAction editAction = new EditAction("Edit Reporting", getDomElement(), getComponent());


        actionMap.put(ActionType.ADD, addAction);
        actionMap.put(ActionType.ADD2, addAction2);
        actionMap.put(ActionType.EDIT, editAction);

        reportingGui.setActionMap(actionMap);
    }


    @Override
    public JComponent getComponent() {
        return reportingGui;
    }

    private void addReporters() {
        if (getDomElement().isValid()) {
            for (Reporter r : myDomElement.getReporters()) {
                ReporterComponent reporterComponent = new ReporterComponent(r);
                addComponent(reporterComponent);
                reportingGui.addComponent(reporterComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        reportingGui.removeAllComponents();

        addReporters();
    }
}
