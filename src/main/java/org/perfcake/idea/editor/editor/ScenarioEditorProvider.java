package org.perfcake.idea.editor.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.ui.DomFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditorProvider;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.ui.ScenarioComponent;
import org.perfcake.idea.model.Scenario;

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

        return new DomFileEditor<ScenarioComponent>(s, "Designer", new ScenarioComponent(s));
        //return DomFileEditor.createDomFileEditor("Designer", Constants.BIG_ICON, p, new PropertyComponentFactory(p));
    }
}
