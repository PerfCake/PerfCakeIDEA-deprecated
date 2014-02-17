package org.perfcake.idea.run;

import com.intellij.ide.util.TreeFileChooser;
import com.intellij.ide.util.TreeFileChooserFactory;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by miron on 17.2.2014.
 */
public class PerfCakeRunConfigurationForm implements PerfCakeRunConfigurationParams {

    private JPanel rootPanel;
    private TextFieldWithBrowseButton scenarioTextField;

    public PerfCakeRunConfigurationForm(final PerfCakeRunConfiguration configuration) {
        final Project myProject = configuration.getProject();

        /*FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false){
            @Override
            public boolean isFileVisible(VirtualFile file, boolean showHiddenFiles) {
                return (file.isDirectory() || file.getExtension().equals("xml"));
            }
        }*/

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TreeFileChooser fileChooser = TreeFileChooserFactory.getInstance(myProject).createFileChooser("Select Scenario", null, StdFileTypes.XML, null);
                fileChooser.showDialog();

                if (fileChooser.getSelectedFile() != null) {
                    String name = fileChooser.getSelectedFile().getName();
                    setScenarioName(name);
                }
            }
        };
        scenarioTextField.addActionListener(listener);

    }

    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String getScenarioName() {
        return FileUtil.toSystemIndependentName(scenarioTextField.getText().trim());
    }

    @Override
    public void setScenarioName(String name) {
        scenarioTextField.setText(name == null ? "" : FileUtil.toSystemDependentName(name));
    }
}
