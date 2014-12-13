package org.perfcake.idea.editor.dialogs;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.xml.DomElement;

import java.awt.*;

/**
 * Created by miron on 1. 12. 2014.
 */
public abstract class MyDialogWrapper extends DialogWrapper {

    public MyDialogWrapper(Component parent, boolean canBeParent) {
        super(parent, canBeParent);
    }

    public MyDialogWrapper(boolean canBeParent) {
        super(canBeParent);
    }

    public abstract DomElement getMockCopy();
}
