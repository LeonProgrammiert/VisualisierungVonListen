package ui.legos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CustomButton extends CustomPanel {
    private java.util.List<ActionListener> listeners = new ArrayList<>();

    protected JLabel textLabel;

    public CustomButton(String buttonText, int fontSize) {

        // Text
        textLabel = new JLabel(buttonText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Hover
        addMouseListener(new CustomMouseListener(this, textLabel));

       //Click Event
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                fireActionEvent();
            }
        });
        // Format
        setLayout(new GridBagLayout());
        add(textLabel);

    }

    public void setText(String text){
        textLabel.setText(text);
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    private void fireActionEvent() {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getName());
        for (ActionListener l : listeners) {
            l.actionPerformed(evt);
        }
    }
}
