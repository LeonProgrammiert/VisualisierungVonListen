package ui.legos;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class CustomButton extends CustomPanel {

    private java.util.List<ActionListener> actionListeners = new ArrayList<>();
    protected JLabel textLabel;

    public CustomButton(String buttonText, int fontSize) {

        // Text
        textLabel = new JLabel(buttonText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mange click events and add hover effect
        addMouseListener(new CustomMouseListener(this, textLabel));

        // Format
        setLayout(new GridBagLayout());
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

    public void setNewSize(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void setNewSize(int width, int height) {
        setNewSize(new Dimension(width, height));
    }
}
