package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Launcher::createUI);
    //Hey Swing, bitte führe diese Methode (createUI) aus – aber im richtigen GUI-Thread! + invokeLater//Übergibt Code an diesen GUI-Thread
    }

    public static void createUI() {
        JFrame frame = new JFrame("Visualisierung von Listen"); //Neues Fenster mit Titel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //Schließt Programm, wenn das Fenster schließt

        frame.setSize(2000, 1600);

        // Hintergrund
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(24, 26, 28));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //alle container untereinander

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

        // Container für alle Buttons (untereinander, zentriert)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(mainPanel.getBackground());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons einfügen
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); //wie margin in css, abstand zwischen den buttons
        buttonPanel.add(createIconButton("+", "Neue Liste")); // button besteht aus Icon+Text, wird per Methode createIconButton(...) gebaut
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen"));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCE4", "Exportieren"));

        //

        mainPanel.add(Box.createVerticalGlue());        // flexibler Platzfüller, der überschüssigen Raum verteilt. Alles vertikal mittig
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitle1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); //fester platzhalter
        mainPanel.add(subtitle2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        frame.add(mainPanel);
        //Fügt mainPanel in das Fenster (JFrame) ein. mainPanel enthält ganzen Inhalt: Titel, Buttons, Texte
        frame.setVisible(true);
        //Macht das Fenster sichtbar auf dem Bildschirm

    }

    private static JPanel createIconButton(String icon, String labelText) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //icon oben text unten
        panel.setBackground(new Color(40, 40, 40));
        panel.setMinimumSize(new Dimension(160, 80));
        panel.setPreferredSize(new Dimension(160, 90)); //Wenn Platz, dann genau die maße
        panel.setMaximumSize(new Dimension(160, 90));// nicht größer als das
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1)); // erst sichtbar beim Hover (wenn rosa wird)
        panel.setOpaque(true); //zeigt seine hintergrundfarbe an


        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER); //erstellt neues Label das Icon anzeigt
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Genau wie oben, aber diesmal für den Button-Text
        JLabel textLabel = new JLabel(labelText, SwingConstants.CENTER);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textLabel.setForeground(Color.LIGHT_GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue()); //flexiblen, unsichtbaren Platz über dem Icon
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8))); //festen Abstand
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());

        // Hover-Effekt
        panel.addMouseListener(new MouseAdapter() { //Hey Panel, hör ab jetzt auf Mausbewegungen und erstelle einen zuhörer
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(65, 65, 65));
                panel.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 1));
                textLabel.setForeground(new Color(255, 182, 193)); // ← Rosa Text beim Hover

            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(40, 40, 40));
                panel.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1));
                textLabel.setForeground(Color.LIGHT_GRAY);
            }
        });

        return panel;
    }
}