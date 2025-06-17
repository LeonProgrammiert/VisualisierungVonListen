package ui.legos;

import javax.swing.*;
import java.awt.*;

public class CustomIconButton extends CustomButton {


    public CustomIconButton(String icon, String labelText) {
        super(labelText, 13);

        removeAll(); // Remove all components

        // Icon
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Format
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue()); // Flexibler Abstand oben
        add(iconLabel);
        add(Box.createRigidArea(new Dimension(0, 8))); // Fester Abstand
        add(textLabel);
        add(Box.createVerticalGlue()); // Flexibler Abstand unten
    }
}
