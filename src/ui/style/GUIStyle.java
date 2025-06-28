package ui.style;

import javax.swing.*;
import java.awt.*;

public class GUIStyle {
    private static final String fontName = "SansSerif";
    private static final int fontStyle = Font.PLAIN;

    private static Color fontColor;
    private static Color highlightColor;
    private static Color backgroundColor;

    private static Color unhighlightedButtonColor;
    private static Color highlightedButtonColor;
    private static Color unhighlightedBorderColor;

    public static void setColorMode(boolean darkMode) {
        if (darkMode) {
            // Dark mode
            fontColor = Color.WHITE;
            highlightColor = new Color(255, 182, 193);
            backgroundColor = new Color(24, 26, 28);
            unhighlightedButtonColor = new Color(40,40,40);
            highlightedButtonColor = new Color(65, 65, 65);
            unhighlightedBorderColor = new Color(89, 89, 89);
        } else {
            // Light mode
            fontColor = Color.BLACK;
            highlightColor = new Color(224, 14, 46);
            backgroundColor = new Color(240, 242, 244);
            unhighlightedButtonColor = new Color(230, 232, 234);
            highlightedButtonColor = new Color(215, 217, 219);
            unhighlightedBorderColor = new Color(15, 15, 15);
        }
    }

    public GUIStyle(boolean darkMode) {

    }


    // JLabels
    public static JLabel getStyledLabel(String message, Font font, Color color) {
        JLabel label = new JLabel(message, JLabel.CENTER);

        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JLabel getStyledLabel(String message, int fontSize, int fontStyle, Color color) {
        return getStyledLabel(message, getFont(fontSize, fontStyle), color);
    }

    public static JLabel getStyledLabel(String message, int fontSize, Color color) {
        return getStyledLabel(message, fontSize, fontStyle, color);
    }

    public static JLabel getStyledLabel(String message, int fontSize) {
        return getStyledLabel(message, fontSize, fontColor);
    }


    // Fonts
    public static Font getFont(int fontSize, int fontStyle) {
        return new Font(fontName, fontStyle, fontSize);
    }

    public static Font getFont(int fontSize) {
        return getFont(fontSize, fontStyle);
    }

    public static Color getFontColor() {
        return fontColor;
    }

    public static Color getHighlightedColor() {
        return highlightColor;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static Color getButtonColor() {
        return unhighlightedButtonColor;
    }

    public static Color getUnhighlightedButtonBorderColor() {
        return unhighlightedBorderColor;
    }

    public static Color getHighlightedButtonColor() {
        return highlightedButtonColor;
    }


    // Frame
    public static Dimension getFrameSize() {
        return new Dimension(1400, 1000);
    }

}
