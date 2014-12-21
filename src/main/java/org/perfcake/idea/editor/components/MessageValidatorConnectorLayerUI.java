package org.perfcake.idea.editor.components;

import org.perfcake.idea.model.Scenario;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miron on 21. 12. 2014.
 */
public class MessageValidatorConnectorLayerUI extends LayerUI<ScenarioPanel> {
    private static Map<Scenario, MessageValidatorConnectorLayerUI> instances = new HashMap<>();

//    public MessageValidatorConnectorLayerUI() {
//        getV
//        instances.put()
//    }
//
//    public static void getInstance(Message message){
//        getV
//    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        ScenarioPanel scenarioPanel = (ScenarioPanel) ((JLayer) c).getView();
        Map<JComponent, java.util.List<JComponent>> messageValidators = scenarioPanel.getMessageValidators();

        Graphics2D g2 = (Graphics2D) g.create();

        for (JComponent message : messageValidators.keySet()) {
            Rectangle u = SwingUtilities.convertRectangle(message.getParent(), message.getBounds(), c);
            for (JComponent validator : messageValidators.get(message)) {
                Rectangle l = SwingUtilities.convertRectangle(validator.getParent(), validator.getBounds(), c);

                //allow antialiasing
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1.0f, new float[]{2f, 2f}, 2.0f));
                g2.drawLine(u.x + u.width / 2, u.y + u.height, l.x + l.width / 2, l.y);
            }
        }


        g2.dispose();
    }
}
