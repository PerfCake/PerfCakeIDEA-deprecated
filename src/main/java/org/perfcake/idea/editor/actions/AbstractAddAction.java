package org.perfcake.idea.editor.actions;

import com.intellij.icons.AllIcons;

import javax.swing.*;

/**
 * Created by miron on 1. 12. 2014.
 */
public abstract class AbstractAddAction extends AbstractAction {

    public AbstractAddAction(String name) {
        super(name, AllIcons.General.Add);
    }
}
