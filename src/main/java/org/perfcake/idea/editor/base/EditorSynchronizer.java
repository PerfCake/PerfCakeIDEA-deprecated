package org.perfcake.idea.editor.base;

import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miron on 30.5.2014.
 */
public class EditorSynchronizer implements ModuleComponent {

    public EditorSynchronizer(Module module) {

        //TODO use project message bus?
        module.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                if(event.getOldEditor() instanceof TextEditor && event.getNewEditor() instanceof ScenarioEditor &&
                        event.getOldFile().getPath().equals(event.getNewFile().getPath())){
                    //Tab switch from TextEditor to ScenarioEditor, we should update GUI model from TextEditor file content
                    ((ScenarioEditor) event.getNewEditor()).updateEditor();
                }
            }
        });
    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "EditorSynchronizer";
    }

    public void projectOpened() {
        // called when project is opened
    }

    public void projectClosed() {
        // called when project is being closed
    }

    public void moduleAdded() {
        // Invoked when the module corresponding to this component instance has been completely
        // loaded and added to the project.
    }
}
