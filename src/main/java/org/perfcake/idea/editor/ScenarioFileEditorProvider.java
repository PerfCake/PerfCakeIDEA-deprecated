package org.perfcake.idea.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miron on 25.3.2014.
 */
public class ScenarioFileEditorProvider implements FileEditorProvider, DumbAware {
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        // Accept files that have "xmlExample" as the root tag
        PsiManager psiMgr = PsiManager.getInstance(project);
        PsiFile psiFile = psiMgr.findFile(file);
        if (psiFile == null || !(psiFile instanceof XmlFile)) {
            return false;
        }
        return true;
        //TODO accept only scenario files not all XMLs
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        return new ScenarioFileEditor(file);
    }

    @Override
    public void disposeEditor(@NotNull FileEditor editor) {

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
