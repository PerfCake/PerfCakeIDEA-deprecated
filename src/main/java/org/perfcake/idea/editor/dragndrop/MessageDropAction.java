package org.perfcake.idea.editor.dragndrop;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import org.jetbrains.annotations.NotNull;
import org.perfcake.idea.editor.dialogs.MessageDropDialog;
import org.perfcake.idea.model.Message;
import org.perfcake.idea.model.Messages;
import org.perfcake.idea.util.PerfCakeIdeaUtil;

/**
 * Created by miron on 8. 12. 2014.
 */
public class MessageDropAction implements DropAction {

    private Messages messages;

    public MessageDropAction(Messages messages) {
        this.messages = messages;
    }

    @Override
    public boolean invoke(String elementToAdd) {
        if (messages.isValid()) {
            final Messages mockCopy = PerfCakeIdeaUtil.runCreateMockCopy(messages);
            final Message mockMessage = mockCopy.addMessage();

            final MessageDropDialog dialog = new MessageDropDialog(mockMessage);
            boolean ok = dialog.showAndGet();

            if (ok) {
                new WriteCommandAction(messages.getModule().getProject(), "add Message", messages.getXmlElement() == null ? null : messages.getXmlElement().getContainingFile()) {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        mockMessage.copyFrom(dialog.getMockCopy());
                        messages.copyFrom(mockCopy);
                    }
                }.execute();
                return true;
            }
        }
        return false;
    }
}
