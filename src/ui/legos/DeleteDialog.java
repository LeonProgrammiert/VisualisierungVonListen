package ui.legos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

public class DeleteDialog extends JDialog {

    public interface DeleteActionListener {
        void onDeleteCurrent();
        void onDeleteAll();
    }

    public DeleteDialog(JFrame parent, DeleteActionListener listener) {
        super(parent, "Element löschen", true);
        setLayout(new BorderLayout());
        setSize(500, 160);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(24, 26, 28));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel message = new JLabel("Was möchtest du löschen?", SwingConstants.CENTER);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(24, 26, 28));

        // Hilfsmethode für Button-Styling
        JButton cancelButton = createStyledButton("Abbrechen");
        cancelButton.addActionListener(e -> dispose());

        JButton deleteCurrent = createStyledButton("Nur aktuelles Element");
        deleteCurrent.addActionListener(e -> {
            dispose();
            listener.onDeleteCurrent();
        });

        JButton deleteAll = createStyledButton("Komplette Liste");
        deleteAll.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    DeleteDialog.this,
                    "Bist du sicher, dass du die gesamte Liste löschen willst?",
                    "Sicher?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                listener.onDeleteAll();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteCurrent);
        buttonPanel.add(deleteAll);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(new Color(55, 55, 55)); // heller als Hintergrund
        button.setForeground(new Color(230, 230, 230)); // heller Text
        button.setFocusPainted(false);
        button.setOpaque(true); // WICHTIG: Macht Hintergrund sichtbar


        // Statische äußere Border (unsichtbar, reserviert Platz für Linie)
        Border fixedLine = BorderFactory.createLineBorder(new Color(55, 55, 55), 1);
        Border padding = BorderFactory.createEmptyBorder(8, 16, 8, 16); // gleichmäßiges Padding
        button.setBorder(BorderFactory.createCompoundBorder(fixedLine, padding));

        // Hover-Effekt
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(75, 75, 75)); // heller beim Hover
                button.setForeground(new Color(255, 182, 193)); // Rosa Text
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 182, 193), 1),
                        padding
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(55, 55, 55)); // zurück zur Grundfarbe
                button.setForeground(new Color(230, 230, 230)); // heller Text
                button.setBorder(BorderFactory.createCompoundBorder(
                        fixedLine,
                        padding
                ));
            }
        });

        return button;
    }

}