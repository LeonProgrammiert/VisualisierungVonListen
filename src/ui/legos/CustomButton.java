package ui.legos;

import ui.style.GUIStyle;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.Border;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class CustomButton extends CustomPanel {

    private final ArrayList<ActionListener> actionListeners = new ArrayList<>();
    protected JLabel textLabel;

    public CustomButton(String buttonText, int fontSize) {
        this(buttonText, fontSize, new EmptyBorder(5, 5, 5, 5));
    }

    public CustomButton(String buttonText, int fontSize, Border padding) {
        // Initialize
        Border emptyBorder = padding == null ? BorderFactory.createEmptyBorder(5, 5, 5, 5) : padding;
        Border unhighlightedBorder = BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1);

        // Text
        textLabel = GUIStyle.getStyledLabel(buttonText, fontSize);

        // Format
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(unhighlightedBorder, emptyBorder));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mange click events and add hover effect
        addMouseListener(new CustomMouseListener(this, textLabel, emptyBorder));

        add(textLabel);
    }

    public void setText(String text){
        textLabel.setText(text);
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void fireActionEvent() {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getName());
        for (ActionListener l : actionListeners) {
            l.actionPerformed(evt);
        }
    }
}
