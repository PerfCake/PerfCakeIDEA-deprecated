package org.perfcake.idea.editor.ui.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.commands.PropertyEditCommandAction;
import org.perfcake.idea.editor.components.JTitledRoundedRectangle;
import org.perfcake.idea.editor.dialogs.PropertyEditDialog;
import org.perfcake.idea.model.PropertyModel;
import org.perfcake.model.Property;

import javax.xml.bind.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;

/**
 * Created by miron on 29.4.2014.
 */
public class PropertyRectangle extends JTitledRoundedRectangle implements PropertyChangeListener {
    private PropertyModel model;

    public PropertyRectangle(@NotNull PropertyModel model) {
        super(model.toString());
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    @NotNull
    public PropertyModel getModel() {
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        label.setText(model.toString());
    }

    public String toString() {
        return getClass().getName() + " " + model.toString();
    }

    @Override
    public void invokeDialog(PsiFile scenarioPsi) {
        PropertyEditDialog propertyEditDialog = new PropertyEditDialog(scenarioPsi.getProject(), model.getProperty().getName(), model.getProperty().getValue());
        propertyEditDialog.show();
        if (propertyEditDialog.isOK()) {
            model.setName(propertyEditDialog.getNameText());
            model.setValue(propertyEditDialog.getValueText());
            Property property = model.getProperty();

            java.io.StringWriter sw = new StringWriter();
            try {
                JAXBContext jc = JAXBContext.newInstance(Property.class);
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                marshaller.marshal(property, sw);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            /*XmlFile xmlFile = (XmlFile) psiFile;
        XmlDocument doc = xmlFile.getDocument();
        XmlTag root = doc.getRootTag();
        if(root != null){
            XmlTag xmlParent = root.findFirstSubTag( "someTag" );
            if(xmlParent != null){
                CommandProcessor.getInstance().executeCommand(xmlFile.getProject(), new CommandPsiWriteTag, "Add child tags", "My Group");
                model.commit;*/
            XmlFile xml = (XmlFile) scenarioPsi;
            XmlDocument doc = xml.getDocument();
            XmlTag root = doc.getRootTag();
            if(root != null){
                final XmlTag parent = root.findFirstSubTag("properties");
                /*if(parent != null){
                    CommandProcessor.getInstance().executeCommand(scenarioPsi.getProject(), new WriteAction<>() {
                        @Override
                        protected void run(Result<Object> result) throws Throwable {
                            XmlTag newTag = parent.createChildTag("Jajajaja", parent.getNamespace(), "test", false );
                            parent.addSubTag(newTag, false);
                        }
                    }, "Write Element", "My ID");

                }*/
            }



            //PropertyEditCommandAction propertyEditCommandAction = new PropertyEditCommandAction(scenarioPsi.getProject(), scenarioPsi, propertyEditDialog);
            //propertyEditCommandAction.execute();
            //model.setName(propertyEditDialog.getNameText());
            //model.setValue(propertyEditDialog.getValueText());
        }
    }
}
