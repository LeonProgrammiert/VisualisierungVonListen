package ui.style;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUIStyle {
    private static final String fontName = "SansSerif";
    private static final int fontStyle = Font.PLAIN;

    private static Color fontColor;
    private static Color highlightColor;
    private static Color backgroundColor;


    private static Color buttonUnavailableColor;
    private static Color unhighlightedButtonColor;
    private static Color highlightedButtonColor;

    private static Color unhighlightedBorderColor;

    private static String highlightedSaveImage;
    private static String availableSaveImage;
    private static String unavailableSaveImage;

    public static void setColorMode(boolean darkMode) {
        if (darkMode) {
            // Dark mode
            fontColor = Color.WHITE;

            highlightColor = new Color(255, 182, 193);
            backgroundColor = new Color(24, 26, 28);

            buttonUnavailableColor = new Color(70, 70, 70);
            unhighlightedButtonColor = new Color(40,40,40);
            highlightedButtonColor = new Color(65, 65, 65);

            highlightedSaveImage = "src/assets/saveIconPink.png";
            availableSaveImage = "src/assets/saveIconWhite.png";
            unavailableSaveImage = "src/assets/saveIconGray.png";

            unhighlightedBorderColor = new Color(89, 89, 89);
        } else {
            // Light mode
            fontColor = Color.BLACK;

            highlightColor = new Color(0, 179, 255);
            backgroundColor = new Color(240, 242, 244);

            buttonUnavailableColor = new Color(147, 147, 147);
            unhighlightedButtonColor = new Color(230, 232, 234);
            highlightedButtonColor = new Color(215, 217, 219);

            highlightedSaveImage = "src/assets/saveIconBlue.png";
            availableSaveImage = "src/assets/saveIconBlack.png";
            unavailableSaveImage = "src/assets/saveIconLightGray.png";

            unhighlightedBorderColor = new Color(15, 15, 15);
        }
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

    public static Color getButtonUnavailableColor() {
        return buttonUnavailableColor;
    }

    // Frame
    public static Dimension getFrameSize() {
        return new Dimension(1400, 800);
    }


    // Files
    public static File getClickSoundFile() {
        return new File(System.getProperty("user.dir") + "/src/assets/clickSound.wav");
    }

    public static File getErrorSoundFile() {
        return new File(System.getProperty("user.dir") + "/src/assets/errorSound.wav");
    }

    public static File getSwapSoundFile() {
        return new File(System.getProperty("user.dir") + "/src/assets/swapSound.wav");
    }

    public static String getHighlightedSaveImage() {
        return highlightedSaveImage;
    }

    public static String getAvailableSaveImage() {
        return availableSaveImage;
    }

    public static String getUnavailableSaveImage() {
        return unavailableSaveImage;
    }

    public static Image getWindowIcon(){
        return new ImageIcon("src/assets/windowIcon.png").getImage();
    }
}
