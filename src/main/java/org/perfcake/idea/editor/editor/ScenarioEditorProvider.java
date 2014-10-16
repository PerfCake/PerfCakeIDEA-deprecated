package org.perfcake.idea.editor.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.impl.DomApplicationComponent;
import com.intellij.util.xml.ui.DomFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditorProvider;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.Constants;
import org.perfcake.idea.model.Property;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.module.ScenarioDomFileDescription;

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
        return !(manager.getFileElement((XmlFile) psiFile, Scenario.class) == null);
    }

    @NotNull
    @Override
    public PerspectiveFileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        PsiManager psiMan = PsiManager.getInstance(project);
        XmlFile scenario = (XmlFile) psiMan.findFile(file);
        Scenario s = (Scenario) DomManager.getDomManager(project).getFileElement(scenario, Scenario.class).getRootElement();
        Property p = s.getProperties().getProperties().get(0);
        return DomFileEditor.createDomFileEditor("Designer", Constants.BIG_ICON, p, new PropertyComponentFactory(p));
    }
}