package ui.legos;

import javax.swing.*;
import java.awt.*;
// Grundstruktur für alles, was wie ein Button aussehen und sich verhalten soll.
public class CustomPanel extends JPanel {

    public CustomPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 40, 40));

        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        setOpaque(true);
    }
}
