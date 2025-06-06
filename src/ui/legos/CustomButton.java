package ui.legos;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends CustomPanel{
    public CustomButton(String buttonText, int fontSize) {

        // Text
        JLabel textLabel = new JLabel(buttonText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Hover
        addMouseListener(new CustomMouseListener(this, textLabel));

        // Format
        setLayout(new GridBagLayout());
        add(textLabel);


    }
}
