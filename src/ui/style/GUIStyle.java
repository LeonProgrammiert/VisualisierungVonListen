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


    // JLabels
    public static JLabel getStyledLabel(String message, Font font, Color color) {
        JLabel label = new JLabel(message, JLabel.CENTER);

        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JLabel getStyledLabel(String message, int fontSize, FontStyle fontStyle, Color color) {
        return getStyledLabel(message, getFont(fontSize, fontStyle), color);
    }

    public static JLabel getStyledLabel(String message, int fontSize, Color color) {
        return getStyledLabel(message, fontSize, fontStyle, color);
    }

    public static JLabel getStyledLabel(String message, int fontSize) {
        return getStyledLabel(message, fontSize, white);
    }


    // Fonts
    public static Font getFont(int fontSize, FontStyle fontStyle) {
        return new Font(fontName, fontStyle.getValue(), fontSize);
    }
    public static Font getFont(int fontSize) {
        return getFont(fontSize, fontStyle);
    }



}
