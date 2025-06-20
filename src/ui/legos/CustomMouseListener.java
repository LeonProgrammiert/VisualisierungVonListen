package ui.legos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//Diese Klasse sorgt für den Hover-Effekt
public class CustomMouseListener extends MouseAdapter {

    private final CustomButton parent;
    private final JLabel label;
    private final Border emptyBorder;
    private final Border highlightBorder;
    private final Border unhighlightedBorder;

    public CustomMouseListener(CustomButton parent, JLabel label) {
        this.parent = parent;
        this.label = label;
        emptyBorder = new EmptyBorder(5, 5, 5, 5);
        highlightBorder = BorderFactory.createLineBorder(new Color(255, 182, 193), 1);
        unhighlightedBorder = BorderFactory.createLineBorder(new Color(40, 40, 40), 1);
    }

    public void mouseEntered(MouseEvent e) {
        parent.setBackground(new Color(65, 65, 65));
        parent.setBorder(BorderFactory.createCompoundBorder(highlightBorder, emptyBorder));
        label.setForeground(new Color(255, 182, 193)); // ← Rosa Text beim Hover
    }

    public void mouseExited(MouseEvent e) {
        parent.setBackground(new Color(40, 40, 40));
        parent.setBorder(BorderFactory.createCompoundBorder(unhighlightedBorder, emptyBorder));
        label.setForeground(Color.LIGHT_GRAY);
    }

    public void mouseClicked(MouseEvent e) {
        parent.fireActionEvent();
    }
}
