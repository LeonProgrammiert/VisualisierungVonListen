package ui.legos;

import ui.style.GUIStyle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Grundstruktur für alles, was wie ein Button aussehen und sich verhalten soll.
public class CustomPanel extends JPanel {

    protected Border unhighlightedBorder;

    public CustomPanel() {
        setLayout(new GridBagLayout());
        setBackground(GUIStyle.getButtonColor());

        setBorder(BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        setOpaque(true);

        unhighlightedBorder = BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1);
    }

    public void setNewSize(int width, int height) {
        setNewSize(new Dimension(width, height));
    }

    public void setNewSize(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }
}
