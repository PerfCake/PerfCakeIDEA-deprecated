package org.perfcake.idea.run;

import com.intellij.icons.AllIcons;

import javax.swing.*;
import java.awt.*;

/**
 * Created by miron on 21. 12. 2014.
 */
public class ConsoleContentWrapper extends JPanel {

    private JComponent consoleContent;
    private MyToolbar toolbar;

    public ConsoleContentWrapper(JComponent consoleContent) {
        super(new BorderLayout());
        this.consoleContent = consoleContent;
        toolbar = new MyToolbar();

        add(consoleContent, BorderLayout.CENTER);
        add(toolbar, BorderLayout.WEST);
    }

    public JComponent getConsoleContent() {
        return consoleContent;
    }

    public MyToolbar getToolbar() {
        return toolbar;
    }

    public class MyToolbar extends JToolBar {
        private JButton stopButton;

        public MyToolbar() {
            super(JToolBar.VERTICAL);
            setFloatable(false);
            setRollover(true);

            stopButton = new JButton(AllIcons.Actions.Suspend);
            add(stopButton);
        }

        public JButton getStopButton() {
            return stopButton;
        }
    }
}
