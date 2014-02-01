package org.perfcake.idea.module;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;

import java.util.Properties;

/**
 * Created by miron on 1.2.2014.
 */
public class PerfCakeFileTemplates {
    public static final String SCENARIO = "/scenario.xml";

    public static PsiElement createFromTemplate(Project project, VirtualFile root, String template, String file, Properties properties) throws Exception{
        root.refresh(false, false);
        PsiDirectory dir = PsiManager.getInstance(project).findDirectory(root);
        if(dir != null){
            createFromTemplate(template, file, dir, properties);
        }
        return null;
    }
    public static PsiElement createFromTemplate(String template, String file, PsiDirectory dir, Properties properties) throws Exception {
        FileTemplateManager manager = FileTemplateManager.getInstance();
        FileTemplate fileTemplate = manager.getInternalTemplate(template);
        return FileTemplateUtil.createFromTemplate(fileTemplate, file, properties, dir);
    }
}
