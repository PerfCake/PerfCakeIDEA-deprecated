package org.perfcake.idea.editor.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.ui.DomFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.components.ScenarioComponent;
import org.perfcake.idea.model.Scenario;

/**
 * Created by miron on 10. 10. 2014.
 */
public class ScenarioEditorProvider extends WeighedFileEditorProvider {
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
        Scenario s = DomManager.getDomManager(project).getFileElement(scenario, Scenario.class).getRootElement();

        DomFileEditor<ScenarioComponent> designer = new DomFileEditor<>(s, "Designer", new ScenarioComponent(s));

        return designer;
    }

    @Override
    public void disposeEditor(@NotNull FileEditor editor) {
        Disposer.dispose(editor);
    }

    @NotNull
    @Override
    public FileEditorState readState(@NotNull Element sourceElement, @NotNull Project project, @NotNull VirtualFile file) {
        return FileEditorState.INSTANCE;
    }

    @Override
    public void writeState(@NotNull FileEditorState state, @NotNull Project project, @NotNull Element targetElement) {

    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return getClass().getName();
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR;
    }

}
