package ui.legos;

import ui.style.GUIStyle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//Diese Klasse sorgt für den Hover-Effekt
public class CustomMouseListener extends MouseAdapter {

    private final CustomButton parent;
    private final JLabel label;

    private final Border emptyBorder;
    private final Border highlightBorder;
    private final Border unhighlightedBorder;

    public CustomMouseListener(CustomButton parent, JLabel label, Border padding) {
        this.parent = parent;
        this.label = label;
        emptyBorder = padding;
        highlightBorder = BorderFactory.createLineBorder(GUIStyle.getPinkColor(), 1);
        unhighlightedBorder = BorderFactory.createLineBorder(GUIStyle.getGrayButtonColor(), 1);
    }

    public void mouseEntered(MouseEvent e) {
        parent.setBackground(GUIStyle.getGrayButtonHighlightedColor());
        parent.setBorder(BorderFactory.createCompoundBorder(highlightBorder, emptyBorder));
        label.setForeground(GUIStyle.getPinkColor()); // ← Rosa Text beim Hover
    }

    public void mouseExited(MouseEvent e) {
        parent.setBackground(GUIStyle.getGrayButtonColor());
        parent.setBorder(BorderFactory.createCompoundBorder(unhighlightedBorder, emptyBorder));
        label.setForeground(GUIStyle.getWhiteColor());
    }

    public void mouseClicked(MouseEvent e) {
        parent.fireActionEvent();
    }
}
