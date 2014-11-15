package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.GeneratorDialog;
import org.perfcake.idea.editor.gui.GeneratorGui;
import org.perfcake.idea.model.Generator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class GeneratorComponent extends BasicDomElementComponent<Generator> {

    private GeneratorGui generatorGui;

    public GeneratorComponent(Generator domElement) {
        super((Generator) domElement.createStableCopy());

        generatorGui = new GeneratorGui(getDomElement().getClazz().getStringValue(), getDomElement().getThreads().getStringValue());
        generatorGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid()) {
                        final Generator mockCopy = (Generator) new WriteAction() {
                            @Override
                            protected void run(@NotNull Result result) throws Throwable {
                                result.setResult(getDomElement().createMockCopy(false));
                            }
                        }.execute().getResultObject();
                        final GeneratorDialog generatorDialog = new GeneratorDialog(generatorGui, mockCopy);
                        boolean ok = generatorDialog.showAndGet();

                        if (ok) {
                            new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Generator", getDomElement().getXmlElement().getContainingFile()) {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    getDomElement().copyFrom(generatorDialog.getMockCopy());
                                    //reset();
                                }
                            }.execute();
                        }

                    }
                }
            }
        });

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

}
