package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Launcher::createUI);
    }

    public static void createUI() {
        JFrame frame = new JFrame("Listen-Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Hintergrund dunkel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // + Button
        JButton plusButton = createDarkButton("+", 100, 100, 36);
        JLabel plusLabel = new JLabel("neue Liste");
        plusLabel.setForeground(Color.LIGHT_GRAY);
        plusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        // Öffnen Button
        JButton oeffnen = createDarkButton("öffnen", 200, 50, 16);

        // Import/Export Button
        JButton eximport = createDarkButton("ex / import", 200, 50, 16);
        eximport.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Hinzufügen
        mainPanel.add(plusButton);
        mainPanel.add(plusLabel);
        mainPanel.add(oeffnen);
        mainPanel.add(eximport);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JButton createDarkButton(String text, int width, int height, int fontSize) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(width, height));
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover-Rahmen
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
            public void mouseExited(MouseEvent e) {
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        return button;
    }
}
