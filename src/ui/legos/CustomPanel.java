package ui.legos;

import javax.swing.*;
import java.awt.*;
// Grundstruktur für alles, was wie ein Button aussehen und sich verhalten soll.
public class CustomPanel extends JPanel {

    public CustomPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //icon oben text unten
        setBackground(new Color(40, 40, 40));

        // Size
        setMinimumSize(new Dimension(160, 80));
        setPreferredSize(new Dimension(160, 90));
        setMaximumSize(new Dimension(160, 90));

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        setOpaque(true);
    }
}
