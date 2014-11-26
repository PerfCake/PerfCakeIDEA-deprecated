package org.perfcake.idea.editor.editor;

import com.intellij.ide.ui.customization.CustomActionsSchema;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.EditorPopupHandler;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.ui.DomFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditorProvider;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.ScenarioComponent;
import org.perfcake.idea.model.Scenario;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioEditorProvider extends PerspectiveFileEditorProvider {
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        PsiManager psiMgr = PsiManager.getInstance(project);
        PsiFile psiFile = psiMgr.findFile(file);
        if (psiFile == null || !(psiFile instanceof XmlFile)) {
            return false;
        }
        DomManager manager = DomManager.getDomManager(project);
        boolean accept = !(manager.getFileElement((XmlFile) psiFile, Scenario.class) == null);
        return accept;
    }

    @NotNull
    @Override
    public PerspectiveFileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        PsiManager psiMan = PsiManager.getInstance(project);
        XmlFile scenario = (XmlFile) psiMan.findFile(file);
        Scenario s = DomManager.getDomManager(project).getFileElement(scenario, Scenario.class).getRootElement();

        DomFileEditor<ScenarioComponent> designer = new DomFileEditor<>(s, "Designer", new ScenarioComponent(s));

        return designer;
        //return DomFileEditor.createDomFileEditor("Designer", Constants.ICON_24P, p, new PropertyComponentFactory(p));
    }

    /**
     * Shows popup menu
     */
    private static final class MyEditorMouseListener extends EditorPopupHandler {
        @Override
        public void invokePopup(final EditorMouseEvent event) {
            if (!event.isConsumed() && event.getArea() == EditorMouseEventArea.EDITING_AREA) {
                ActionGroup group = (ActionGroup) CustomActionsSchema.getInstance().getCorrectedAction(IdeActions.GROUP_EDITOR_POPUP);
                ActionPopupMenu popupMenu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.EDITOR_POPUP, group);
                MouseEvent e = event.getMouseEvent();
                final Component c = e.getComponent();
                if (c != null && c.isShowing()) {
                    popupMenu.getComponent().show(c, e.getX(), e.getY());
                }
                e.consume();
            }
        }
    }


}
