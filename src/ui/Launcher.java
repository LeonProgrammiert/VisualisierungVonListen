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
        JFrame frame = new JFrame("Visualisierung von Listen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Hintergrund dunkel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(24, 26, 28)); // sehr dunkles Grau
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Titel & Untertitel
        JLabel title = new JLabel("Willkommen bei den friedlichen Koalas");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle1 = new JLabel("Hier wird deine Liste visualisiert");
        subtitle1.setFont(new Font("SansSerif", Font.BOLD, 19));
        subtitle1.setForeground(Color.WHITE);
        subtitle1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle2 = new JLabel("Erstelle oder öffne eine Liste");
        subtitle2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle2.setForeground(new Color(255, 182, 193));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button-Container (untereinander, zentriert)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(mainPanel.getBackground());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons einfügen
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("+", "Neue Liste"));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen"));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCE4", "Exportieren"));

        // Alles vertikal mittig
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitle1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(subtitle2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createIconButton(String icon, String labelText) {
        Color normalBg = new Color(40, 40, 40);
        Color hoverBg = new Color(65, 65, 65);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(normalBg);
        panel.setPreferredSize(new Dimension(160, 80));
        panel.setMaximumSize(new Dimension(160, 80));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createLineBorder(normalBg, 1));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(true);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(labelText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());

        // Hover-Effekt
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(hoverBg);
                panel.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 1));
                textLabel.setForeground(new Color(255, 182, 193)); // ← Rosa Text beim Hover

            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(normalBg);
                panel.setBorder(BorderFactory.createLineBorder(normalBg, 1));
                textLabel.setForeground(Color.LIGHT_GRAY);
            }
        });

        return panel;
    }
}