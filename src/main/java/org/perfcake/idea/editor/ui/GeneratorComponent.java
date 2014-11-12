package org.perfcake.idea.editor.ui;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.perfcake.idea.editor.gui.GeneratorGui;
import org.perfcake.idea.model.Generator;

import javax.swing.*;

/**
 * Created by miron on 21.10.2014.
 */
public class GeneratorComponent extends BasicDomElementComponent<Generator> {

    private GeneratorGui generatorGui;

    public GeneratorComponent(Generator domElement) {
        super((Generator) domElement.createStableCopy());

        generatorGui = new GeneratorGui(getDomElement().getClazz().getStringValue(), getDomElement().getThreads().getStringValue()) {
            @Override
            public void invokeDialog() {
                //TODO adjust to generatoreditdialog
//                final Property mockCopy = (Property) new WriteAction() {
//                    @Override
//                    protected void run(@NotNull Result result) throws Throwable {
//                        result.setResult(getDomElement().createMockCopy(false));
//                    }
//                }.execute().getResultObject();
//                final PropertyEditDialog editDialog = new PropertyEditDialog(propertyGui, mockCopy);
//                boolean ok = editDialog.showAndGet();
//                if(ok){
//                    new WriteCommandAction(getDomElement().getModule().getProject(), "Set Property" ,getDomElement().getXmlElement().getContainingFile()) {
//                        @Override
//                        protected void run(@NotNull Result result) throws Throwable {
//                            getDomElement().copyFrom(editDialog.getMockCopy());
//                        }
//                    }.execute();
//                }
            }
        };

        RunComponent runComponent = new RunComponent(domElement.getRun());
        generatorGui.addComponent(runComponent.getComponent());
        addComponent(runComponent);
    }

    @Override
    public JComponent getComponent() {
        return generatorGui;
    }

    @Override
    public void reset() {
        super.reset();
        if (getDomElement().isValid()) {
            generatorGui.setClazz(getDomElement().getClazz().getStringValue());
            generatorGui.setThreads(getDomElement().getThreads().getStringValue());
        }
    }

    private String getGuiTitle() {
        return myDomElement.getClazz().getStringValue() + " (" + myDomElement.getThreads().getStringValue() + ")";
    }
}
