package org.perfcake.idea.editor.components;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.colors.ColorType;
import org.perfcake.idea.editor.dialogs.MessagesDialog;
import org.perfcake.idea.editor.swing.JPerfCakeIdeaRectangle;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.model.Scenario;
import org.perfcake.idea.model.Validation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by miron on 21.10.2014.
 */
public class MessagesComponent extends BasicDomElementComponent<Messages> {

    private static final String TITLE = "Messages";
    private JPerfCakeIdeaRectangle messagesGui;

    public MessagesComponent(Messages domElement) {
        super((Messages) domElement.createStableCopy());

        messagesGui = new JPerfCakeIdeaRectangle(TITLE, ColorType.MESSAGES_FOREGROUND, ColorType.MESSAGES_BACKGROUND);
        messagesGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (getDomElement().isValid() && getDomElement().getParentOfType(Scenario.class, true) != null) {
                        Scenario scenario = getDomElement().getParentOfType(Scenario.class, true);
                        if (scenario.isValid() && scenario.getValidation().isValid()) {
                            final Validation validation = scenario.getValidation();

                            final MessagesValidationPair mockPair = (MessagesValidationPair) new WriteAction() {
                                @Override
                                protected void run(@NotNull Result result) throws Throwable {
                                    MessagesValidationPair pair = new MessagesValidationPair((Messages) getDomElement().createMockCopy(false),
                                            (Validation) validation.createMockCopy(false));
                                    result.setResult(pair);
                                }
                            }.execute().getResultObject();
                            final MessagesDialog messagesDialog = new MessagesDialog(messagesGui, mockPair);
                            boolean ok = messagesDialog.showAndGet();

                            if (ok) {
                                new WriteCommandAction(getDomElement().getModule().getProject(), "Edit Messages", getDomElement().getXmlElement().getContainingFile()) {
                                    @Override
                                    protected void run(@NotNull Result result) throws Throwable {
                                        getDomElement().copyFrom(messagesDialog.getMockPair().getMessages());
                                        validation.copyFrom(messagesDialog.getMockPair().getValidation()); //check again validity?
                                        reset();
                                    }
                                }.execute();
                            }
                        }
                    }
                }
            }
        });

        addMessages();
    }


    @Override
    public JComponent getComponent() {
        return messagesGui;
    }

    private void addMessages() {
        if(getDomElement().isValid()){
            for (Message m : getDomElement().getMessages()) {
                MessageComponent messageComponent = new MessageComponent(m);
                addComponent(messageComponent);
                messagesGui.addComponent(messageComponent.getComponent());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        getChildren().clear();
        messagesGui.removeAllComponents();

        addMessages();
    }
}
