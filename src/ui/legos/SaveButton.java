package ui.legos;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controls.Controller;
import ui.style.GUIStyle;

public class SaveButton extends CustomPanel{
    private final ImageIcon availableIcon;
    private final ImageIcon unavailableIcon;
    private final ImageIcon highlightedIcon;

    private final Color availableColor;
    private final Color unavailableColor;
    private final Color highlightedColor;

    private final JLabel imageContainer;
    private final JLabel textContainer;

    public SaveButton(int fontsize){
        setValues();

        // Initialize images
        availableIcon = loadAndScaleIcon("src/assets/saveIconWhite.png");
        unavailableIcon = loadAndScaleIcon("src/assets/saveIconGray.png");
        highlightedIcon = loadAndScaleIcon("src/assets/saveIconPink.png");

        availableColor = GUIStyle.getFontColor();
        unavailableColor = GUIStyle.getButtonUnavailableColor();
        highlightedColor = GUIStyle.getHighlightedColor();


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
                    Controller.getController().getListEditor().saveList();
                    imageContainer.setIcon(unavailableIcon);
                    textContainer.setForeground(unavailableColor);
                    setBackground(GUIStyle.getButtonColor());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(Controller.unsavedChanges){
                    imageContainer.setIcon(highlightedIcon);
                    textContainer.setForeground(highlightedColor);
                    setBackground(GUIStyle.getHighlightedButtonColor());
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
