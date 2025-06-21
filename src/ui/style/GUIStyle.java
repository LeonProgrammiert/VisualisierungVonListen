package ui.style;

import backend.enumerations.FontStyle;

import javax.swing.*;
import java.awt.*;

public class GUIStyle {
    private static final String fontName = "SansSerif";
    private static final FontStyle fontStyle = FontStyle.PLAIN;

    private static final Color white = Color.WHITE;
    private static final Color pink = new Color(255, 182, 193);

    private static final Color grayBackground = new Color(24, 26, 28);


    public static JLabel getStyledLabel(String message, FontStyle fontStyle, int fontSize, Color color) {
        JLabel label = new JLabel(message, JLabel.CENTER);

        label.setFont(new Font(fontName, fontStyle.getValue(), fontSize));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JLabel getStyledLabel(String message, int fontSize, Color color) {
        return getStyledLabel(message, fontStyle, fontSize, color);
    }

    public static JLabel getStyledLabel(String message, int fontSize) {
        return getStyledLabel(message, fontSize, white);
    }

}
