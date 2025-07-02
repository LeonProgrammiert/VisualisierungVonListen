package ui.style;

import javax.swing.*;
import java.awt.*;

public class GUIStyle {
    private static final String fontName = "SansSerif";
    private static final int fontStyle = Font.PLAIN;

    private static final Color white = Color.WHITE;
    private static final Color pink = new Color(255, 182, 193);
    private static final Color grayBackground = new Color(24, 26, 28);
    private static final Color grayButton = new Color(40,40,40);

    private static final Color buttonUnavailable = new Color(70, 70, 70);

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
        return getStyledLabel(message, fontSize, white);
    }


    // Fonts
    public static Font getFont(int fontSize, int fontStyle) {
        return new Font(fontName, fontStyle, fontSize);
    }

    public static Font getFont(int fontSize) {
        return getFont(fontSize, fontStyle);
    }

    public static Color getWhiteColor() {
        return white;
    }

    public static Color getPinkColor() {
        return pink;
    }

    public static Color getGrayColor() {
        return grayBackground;
    }

    public static Color getGrayButtonColor() {
        return grayButton;
    }

    public static Color getGrayButtonHighlightedColor() {
        return new Color(65, 65, 65);
    }

    public static Color getButtonUnavailableColor() {
        return buttonUnavailable;
    }

    // Frame
    public static Dimension getFrameSize() {
        return new Dimension(1400, 800);
    }

}
