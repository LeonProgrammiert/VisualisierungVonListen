package ui.legos;

import ui.style.GUIStyle;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;

public class CustomListElementPanel extends JPanel {

    public CustomListElementPanel(String data) {
        setLayout(new GridLayout(1, 3)); // 1 Zeile, 3 Spalten
        setBorder(BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1));

        add(getPanel("←"));
        add(getPanel(data));
        add(getPanel("→"));

    }

    private JPanel getPanel(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(GUIStyle.getButtonColor());
        CustomButton label = new CustomButton(text, 28, new EmptyBorder(2, 5, 2, 5));
        panel.add(label);
        return panel;
    }
}

