package ui.legos;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controls.Controller;
import ui.style.GUIStyle;

public class SaveButton extends JPanel{
    private final ImageIcon whiteIcon;
    private final ImageIcon grayIcon;
    private final ImageIcon pinkIcon;

    private final JLabel imageContainer;
    
    public SaveButton(){
        whiteIcon = new ImageIcon("src/assets/saveIconWhite.png");
        grayIcon = new ImageIcon("src/assets/saveIconGray.png");
        pinkIcon = new ImageIcon("src/assets/saveIconPink.png");

        imageContainer = new JLabel(grayIcon);
        
        setBackground(GUIStyle.getGrayButtonColor());
        setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        setSize(60, 60);

        add(imageContainer);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(Controller.unsavedChanges){
                    Controller.getController().getListEditor().saveList();
                    imageContainer.setIcon(grayIcon);
                    setBackground(GUIStyle.getGrayButtonColor());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(Controller.unsavedChanges){
                    imageContainer.setIcon(pinkIcon);
                    setBackground(GUIStyle.getGrayButtonHighlightedColor());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(Controller.unsavedChanges){
                    imageContainer.setIcon(whiteIcon);
                } else {
                    imageContainer.setIcon(grayIcon);
                }
                setBackground(GUIStyle.getGrayButtonColor());
            }
        });
    }

    public void updateSaveAvailability(boolean isUnsaved) {
        if(isUnsaved){
            imageContainer.setIcon(whiteIcon);
        } else {
            imageContainer.setIcon(grayIcon);        
        }
    }
}
