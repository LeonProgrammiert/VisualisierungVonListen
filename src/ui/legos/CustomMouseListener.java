package ui.legos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

//Diese Klasse sorgt für den Hover-Effekt
public class CustomMouseListener extends MouseAdapter {

    private final CustomButton parent;
    private final JLabel label;

    public CustomMouseListener(CustomButton parent, JLabel label) {
        this.parent = parent;
        this.label = label;
    }

    public void mouseEntered(MouseEvent e) {
        parent.setBackground(new Color(65, 65, 65));
        parent.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 1));
        label.setForeground(new Color(255, 182, 193)); // ← Rosa Text beim Hover
    }

    public void mouseExited(MouseEvent e) {
        parent.setBackground(new Color(40, 40, 40));
        parent.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1));
        label.setForeground(Color.LIGHT_GRAY);
    }

    public void mouseClicked(MouseEvent e) {
        parent.fireActionEvent();
    }
}
