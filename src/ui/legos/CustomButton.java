package ui.legos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
}
