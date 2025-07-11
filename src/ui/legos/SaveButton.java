package ui.legos;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

import controls.Controller;
import ui.style.GUIStyle;

public class SaveButton<T> extends CustomPanel{

    private final Controller<T> controller;

    private ImageIcon availableIcon;
    private ImageIcon unavailableIcon;
    private ImageIcon highlightedIcon;

    private Color availableColor;
    private Color unavailableColor;
    private Color highlightedColor;

    private final JLabel imageContainer;
    private final JLabel textContainer;

    public void setTheme() {
        availableColor = GUIStyle.getFontColor();
        highlightedColor = GUIStyle.getHighlightedColor();
        unavailableColor = GUIStyle.getButtonUnavailableColor();

        highlightedIcon = loadAndScaleIcon(GUIStyle.getHighlightedSaveImage());
        availableIcon = loadAndScaleIcon(GUIStyle.getAvailableSaveImage());
        unavailableIcon = loadAndScaleIcon(GUIStyle.getUnavailableSaveImage());

    }

    public SaveButton(int fontsize, Controller<T> controller) {
        this.controller = controller;
        setValues();

        // Initialize
        setTheme();


        // Image container
        imageContainer = new JLabel(unavailableIcon);
        imageContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
        addButtonFunctionality();

        // Text container
        textContainer = GUIStyle.getStyledLabel("Speichern", fontsize);
        // Margin to image
        textContainer.setBorder(new EmptyBorder(0, 5, 0, 0));

        add(imageContainer);
        add(textContainer);

    }

    private void setValues() {
        setBackground(GUIStyle.getButtonColor());
        setBorder(BorderFactory.createCompoundBorder(unhighlightedBorder, new EmptyBorder(5, 5, 5, 5)));
        setSize(60, 60);
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    private void addButtonFunctionality() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(Controller.unsavedChanges){
                    // Play sound
                    controller.playSound(GUIStyle.getClickSoundFile());
                    // Save  list
                    controller.getListEditor().saveList();
                    // Change button appearance
                    imageContainer.setIcon(unavailableIcon);
                    textContainer.setForeground(unavailableColor);
                    setBackground(GUIStyle.getButtonColor());
                    // Change cursor
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(Controller.unsavedChanges){
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    imageContainer.setIcon(highlightedIcon);
                    textContainer.setForeground(highlightedColor);
                    setBackground(GUIStyle.getHighlightedButtonColor());
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(Controller.unsavedChanges){
                    imageContainer.setIcon(availableIcon);
                    textContainer.setForeground(availableColor);
                } else {
                    textContainer.setForeground(unavailableColor);
                    imageContainer.setIcon(unavailableIcon);
                }
                setBackground(GUIStyle.getButtonColor());
            }
        });
    }

    public void updateSaveAvailability(boolean isUnsaved) {
        if(isUnsaved){
            textContainer.setForeground(availableColor);
            imageContainer.setIcon(availableIcon);
        } else {
            textContainer.setForeground(unavailableColor);
            imageContainer.setIcon(unavailableIcon);
        }
    }
    private ImageIcon loadAndScaleIcon(String path) {
        // Image size
        int size = 20;

        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
