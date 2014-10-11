package org.perfcake.idea.editor.commands;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nullable;
import org.perfcake.idea.editor.dialogs.PropertyEditDialog;

/**
 * Created by miron on 2.6.2014.
 */
public class PropertyEditCommandAction extends WriteCommandAction {
    private PsiFile xmlFile;
    private PropertyEditDialog propertyEditDialog;

    public PropertyEditCommandAction(@Nullable Project project, PsiFile psiFile, PropertyEditDialog propertyEditDialog) {
        super(project, psiFile);
        this.propertyEditDialog = propertyEditDialog;

        /*XmlFile xmlFile = (XmlFile) psiFile;
        XmlDocument doc = xmlFile.getDocument();
        XmlTag root = doc.getRootTag();
        if(root != null){
            XmlTag xmlParent = root.findFirstSubTag( "someTag" );
            if(xmlParent != null){
                CommandProcessor.getInstance().executeCommand(xmlFile.getProject(), new CommandPsiWriteTag, "Add child tags", "My Group");
                oldmodel.commit;*/
    }


    @Override
    protected void run(Result result) throws Throwable {
        XmlTag xmlTag;


    }
}
