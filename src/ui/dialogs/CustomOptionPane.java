package ui.dialogs;

import ui.legos.CustomButton;
import ui.style.GUIStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomOptionPane extends JDialog {

    private int result = JOptionPane.CLOSED_OPTION; // Rückgabewert für Confirm-Dialog

    public static void showMessageDialog(Component parent, String title, String message) {
        CustomOptionPane pane = new CustomOptionPane(parent, title, message);
        pane.setVisible(true);
    }

    public static int showConfirmDialog(Component parent, String title, String message) {
        CustomOptionPane pane = new CustomOptionPane(parent, title, message, true);
        pane.setVisible(true);
        return pane.result;
    }


    // --------- Konstruktor für Message-Dialog ---------
    private CustomOptionPane(Component parent, String title, String message) {
        this(parent, title, message, false);
    }

    // --------- Konstruktor für Confirm-Dialog (mehrere Buttons) ---------
    private CustomOptionPane(Component parent, String title, String message, boolean isConfirmDialog) {
        setTitle(title);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(GUIStyle.getBackgroundColor());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel label = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>", SwingConstants.CENTER);
        label.setForeground(GUIStyle.getFontColor());
        label.setFont(GUIStyle.getFont(18));
        label.setOpaque(false);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (isConfirmDialog) {
            // Ja Button
            CustomButton yesButton = new CustomButton("Ja", 16);
            yesButton.addActionListener(e -> {
                result = JOptionPane.YES_OPTION;
                dispose();
            });

            // Nein Button
            CustomButton noButton = new CustomButton("Nein", 16);
            noButton.addActionListener(e -> {
                result = JOptionPane.NO_OPTION;
                dispose();
            });


            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(GUIStyle.getBackgroundColor());
            buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

            buttonsPanel.add(yesButton);
            buttonsPanel.add(noButton);

            panel.add(buttonsPanel);

        } else {
            // Nur OK Button
            CustomButton okButton = new CustomButton("OK", 16);
            okButton.setBorder(new EmptyBorder(5, 15, 5, 15));
            okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            okButton.addActionListener(e -> dispose());

            panel.add(okButton);
        }

        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }
}
