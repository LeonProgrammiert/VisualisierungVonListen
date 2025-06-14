package ui.legos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

public class CustomMouseListener extends MouseAdapter {

    private final JPanel panel;
    private final JLabel label;

    public CustomMouseListener(JPanel component, JLabel label) {
        this.panel = component;
        this.label = label;
    }
    public void mouseEntered(MouseEvent e) {
        panel.setBackground(new Color(65, 65, 65));
        panel.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 1));
        label.setForeground(new Color(255, 182, 193)); // ← Rosa Text beim Hover
    }
    public void mouseExited(MouseEvent e) {
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1));
        label.setForeground(Color.LIGHT_GRAY);
    }
}
