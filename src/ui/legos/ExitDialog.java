package ui.legos;

import javax.swing.*;
import java.awt.*;

public class ExitDialog extends JDialog {

    public interface ExitListener {
        void onSave();
        void onDiscard();
    }
    /*
    public ExitDialog(JFrame parent, ExitListener listener) {
        super(parent, "Änderungen speichern?", true);
        setLayout(new BorderLayout());
        setSize(450, 160);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(24, 26, 28));

        JLabel label = new JLabel("Möchten Sie die Änderungen speichern?", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(24, 26, 28));

        CustomButton cancel = new CustomButton("Abbrechen", 14);
        cancel.addActionListener(e -> dispose());

        CustomButton discard = new CustomButton("Nein", 14);
        discard.addActionListener(e -> {
            dispose();
            listener.onDiscard();
        });
        CustomButton save = new CustomButton("Ja", 14);
        save.addActionListener(e -> {
            dispose();
            listener.onSave();
        });

        buttonPanel.add(cancel);
        buttonPanel.add(discard);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    */
}