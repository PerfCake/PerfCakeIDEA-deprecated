package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaEnablingRectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by miron on 5. 12. 2014.
 */
public class SwitchEnabledAction extends AbstractAction {

    private JPerfCakeIdeaEnablingRectangle enablingRectangle;

    public SwitchEnabledAction(JPerfCakeIdeaEnablingRectangle enablingRectangle, String componentToSwitchState) {
        super((enablingRectangle.isOn() == null) ? "Enable " : (enablingRectangle.isOn() ? "Disable " : "Enable ") + componentToSwitchState, AllIcons.RunConfigurations.LoadingTree);
        this.enablingRectangle = enablingRectangle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enablingRectangle.setOn(enablingRectangle.isOn() == null ? null : !enablingRectangle.isOn());
    }
}
