package ui.legos;

import javax.swing.*;
import java.awt.*;

public class CustomIconButton extends CustomPanel{

    public CustomIconButton(String icon, String labelText) {

        // Icon
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Genau wie oben, aber diesmal für den Button-Text
        // Text
        JLabel textLabel = new JLabel(labelText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Hover
        addMouseListener(new CustomMouseListener(this, textLabel));

        // Format
        add(Box.createVerticalGlue()); //flexiblen, unsichtbaren Platz über dem Icon
        add(iconLabel);
        add(Box.createRigidArea(new Dimension(0, 8))); //festen Abstand
        add(textLabel);
        add(Box.createVerticalGlue());
    }
}
