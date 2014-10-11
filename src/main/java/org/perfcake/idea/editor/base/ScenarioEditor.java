package org.perfcake.idea.editor.base;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.oldui.ScenarioEditorPanel;
import org.perfcake.idea.util.PerfCakeIDEAException;
import org.perfcake.idea.util.ScenarioUtil;
import org.perfcake.model.Scenario;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * Created by miron on 25.3.2014.
 */
public class ScenarioEditor implements FileEditor {
    PsiFile scenarioPsi;
    Document scenarioDocument;

    ScenarioEditorPanel editor;

    FileEditorManager editorManager;


    public ScenarioEditor(final Project project, final VirtualFile scenario) {
        //Text Editor document oldmodel
        this.scenarioDocument = FileDocumentManager.getInstance().getCachedDocument(scenario);
        //Text Editor PSI oldmodel
        this.scenarioPsi = PsiManager.getInstance(project).findFile(scenario);

        editorManager = FileEditorManager.getInstance(project);

        scenarioDocument.addDocumentListener(new DocumentAdapter() {
            @Override
            public void documentChanged(DocumentEvent e) {
                if (editorManager.getSelectedEditor(scenario) == ScenarioEditor.this) {
                    updateEditor();
                }
            }
        });

        Scenario scenarioModel;
        try {
            scenarioModel = ScenarioUtil.parse(scenario);
        } catch (PerfCakeIDEAException e) {
            editor = new ScenarioEditorPanel(scenarioPsi);
            return;
        }
        editor = new ScenarioEditorPanel(scenarioPsi, scenarioModel);
    }

    /**
     * Updates Editor from Text Editor content. Text Editor should contain valid PerfCake XML scenario oldmodel, otherwise Eror editor will be showed.
     */
    public void updateEditor() {
        Scenario scenarioModel;
        try {
            scenarioModel = ScenarioUtil.parse(scenarioDocument.getText());
        } catch (PerfCakeIDEAException e) {
            editor.showInvalidPanel();
            return;
        }
        editor.showValidPanel(scenarioModel);
    }


    @NotNull
    @Override
    public JComponent getComponent() {
        return editor;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "Designer";
    }

    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel level) {
        return FileEditorState.INSTANCE;
    }

    @Override
    public void setState(@NotNull FileEditorState state) {

    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {

    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder() {
        return null;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
