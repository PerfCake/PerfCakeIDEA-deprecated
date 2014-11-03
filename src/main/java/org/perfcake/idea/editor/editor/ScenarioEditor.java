package org.perfcake.idea.editor.editor;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.ui.DomFileEditor;
import org.perfcake.idea.editor.ui.ScenarioComponent;

/**
 * Created by miron on 3.11.2014.
 */
public class ScenarioEditor extends DomFileEditor<ScenarioComponent> {
    public ScenarioEditor(DomElement element, String name, ScenarioComponent component) {
        super(element, name, component);
    }
}
