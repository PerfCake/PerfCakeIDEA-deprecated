package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.MessageDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.model.Validation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class MessageComponent extends BasicDomElementComponent<Message> {

    private JPerfCakeIdeaRectangle messageGui;

    public MessageComponent(Message domElement) {
        super((Message) domElement.createStableCopy());

        messageGui = new JPerfCakeIdeaRectangle(domElement.getUri().getStringValue(), ColorType.MESSAGE_FOREGROUND, ColorType.MESSAGE_BACKGROUND);
        messageGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid() && getDomElement().getParentOfType(Scenario.class, true) != null) {
                        Scenario scenario = getDomElement().getParentOfType(Scenario.class, true);
                        if (scenario.isValid() && scenario.getValidation().isValid()) {
                            final Validation validation = scenario.getValidation();

                            final MessageValidationPair mockPair = (MessageValidationPair) new WriteAction() {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    MessageValidationPair pair = new MessageValidationPair((Message) getDomElement().createMockCopy(false),
                                            (Validation) validation.createMockCopy(false));
                                    result.setResult(pair);
                                }
                            }.execute().getResultObject();
                            final MessageDialog messageDialog = new MessageDialog(messageGui, mockPair);
                            boolean ok = messageDialog.showAndGet();

                            if (ok) {
                                new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Message", getDomElement().getXmlElement().getContainingFile()) {
                                    @Override
                                    protected void run(@NotNull Result result) throws Throwable {
                                        getDomElement().copyFrom(messageDialog.getMockPair().getMessage());
                                        validation.copyFrom(messageDialog.getMockPair().getValidation()); //check again validity?
                                        reset();
                                    }
                                }.execute();
                            }
                        }


                    }
                }
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return messageGui;
    }

    @Override
    public void reset() {
        super.reset();
        if(getDomElement().isValid()){
            messageGui.setTitle(getDomElement().getUri().getStringValue());
        }
    }
}
