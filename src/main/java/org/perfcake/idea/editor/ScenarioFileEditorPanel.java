package org.perfcake.idea.editor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import org.perfcake.PerfCakeException;
import org.perfcake.idea.util.ScenarioHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miron on 15.4.2014.
 */
public class ScenarioFileEditorPanel extends JPanel {
    private static final Logger LOG = Logger.getInstance(ScenarioFileEditorPanel.class);
    private VirtualFile scenario;


    public ScenarioFileEditorPanel(final VirtualFile scenario) {
        this.scenario = scenario;

        setLayout(new GridLayout(3, 2));


        BaseComponent baseComponent1 = new BaseComponent();
        BaseComponent baseComponent2 = new BaseComponent();
        BaseComponent baseComponent3 = new BaseComponent();
        BaseComponent baseComponent4 = new BaseComponent();
        BaseComponent baseComponent5 = new BaseComponent();
        BaseComponent baseComponent6 = new BaseComponent();

        //baseComponent1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        baseComponent1.setLayout(new FlowLayout(FlowLayout.CENTER));
        final JTextField t = new JTextField("Generator");
        t.setPreferredSize(new Dimension(200, 70));
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ScenarioHandler handler = new ScenarioHandler(new URL(scenario.getUrl()));
                    handler.getScenarioModel().getGenerator().setClazz(t.getText());
                    handler.save();
                } catch (PerfCakeException e) {
                    LOG.error("Scenario cannot be parsed", e);
                } catch (MalformedURLException e) {
                    LOG.error("Scenario location cannot be determined", e);
                }
            }
        });


        baseComponent1.add(t);

        add(baseComponent1);
        add(baseComponent2);
        add(baseComponent3);
        add(baseComponent4);
        add(baseComponent5);
        add(baseComponent6);
        //add(baseComponent4);

    }
}
