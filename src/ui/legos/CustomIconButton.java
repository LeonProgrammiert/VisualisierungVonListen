package ui.legos;

import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;

public class CustomIconButton extends CustomButton {

    public CustomIconButton(String icon, String labelText) {
        super(labelText, 13);

        removeAll(); // Remove all components

        // Icon
        JLabel iconLabel = GUIStyle.getStyledLabel(icon, 28);

        // Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Format
        add(Box.createVerticalGlue()); // Flexibler Abstand oben
        add(iconLabel);
        add(Box.createRigidArea(new Dimension(0, 8))); // Fester Abstand
        add(textLabel);
        add(Box.createVerticalGlue()); // Flexibler Abstand unten
    }
}
