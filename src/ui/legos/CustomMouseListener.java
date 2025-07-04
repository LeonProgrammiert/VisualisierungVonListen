package ui.legos;

import ui.style.GUIStyle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

//Diese Klasse sorgt für den Hover-Effekt
public class CustomMouseListener extends MouseAdapter {

    private final CustomButton parent;
    private final JLabel label;

    private final Border emptyBorder;
    private Border highlightBorder;
    private Border unhighlightedBorder;

    public CustomMouseListener(CustomButton parent, JLabel label, Border padding) {
        this.parent = parent;
        this.label = label;
        emptyBorder = padding;
        setTheme();
    }

    public void mouseEntered(MouseEvent e) {
        parent.setBackground(GUIStyle.getHighlightedButtonColor());
        parent.setBorder(BorderFactory.createCompoundBorder(highlightBorder, emptyBorder));
        label.setForeground(GUIStyle.getHighlightedColor()); // ← Rosa Text beim Hover
    }

    public void mouseExited(MouseEvent e) {
        parent.setBackground(GUIStyle.getButtonColor());
        parent.setBorder(BorderFactory.createCompoundBorder(unhighlightedBorder, emptyBorder));
        label.setForeground(GUIStyle.getFontColor());
    }

    public void mouseClicked(MouseEvent e) {
        parent.fireActionEvent();
    }

    public void setTheme() {
        highlightBorder = BorderFactory.createLineBorder(GUIStyle.getHighlightedColor(), 1);
        unhighlightedBorder = BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1);
    }
}
