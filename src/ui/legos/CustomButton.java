package ui.legos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class CustomButton extends CustomPanel {

    protected JLabel textLabel;

    public CustomButton(String buttonText, int fontSize) {

        // Text
        textLabel = new JLabel(buttonText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Hover
        addMouseListener(new CustomMouseListener(this, textLabel));

        // Format
        setLayout(new GridBagLayout());
        add(textLabel);
    }
    // in CustomButton.java
    private final JButton hiddenButton = new JButton();

    public void addActionListener(ActionListener l) {
        hiddenButton.addActionListener(l);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (ActionListener al : hiddenButton.getActionListeners()) {
                    al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
    }
    public void setText(String text){
        textLabel.setText(text);
    }
}
