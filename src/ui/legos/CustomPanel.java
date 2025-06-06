package ui.legos;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

    public CustomPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //icon oben text unten
        setBackground(new Color(40, 40, 40));
        setMinimumSize(new Dimension(160, 80));
        setPreferredSize(new Dimension(160, 90)); //Wenn Platz, dann genau die maße
        setMaximumSize(new Dimension(160, 90));// nicht größer als das
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        setOpaque(true);
    }
}
