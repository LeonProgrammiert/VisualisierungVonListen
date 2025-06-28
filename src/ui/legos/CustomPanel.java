package ui.legos;

import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;
// Grundstruktur für alles, was wie ein Button aussehen und sich verhalten soll.
public class CustomPanel extends JPanel {

    public CustomPanel() {
        setLayout(new GridBagLayout());
        setBackground(GUIStyle.getButtonColor());

        setBorder(BorderFactory.createLineBorder(GUIStyle.getButtonColor(), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        setOpaque(true);
    }
}
