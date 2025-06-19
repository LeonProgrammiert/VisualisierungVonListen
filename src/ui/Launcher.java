package ui;

import ui.legos.CustomIconButton;
import controls.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Launcher extends JFrame {

    private final Controller controller;

    enum eventTypes {add, open, export}

    public Launcher(Controller controller) {
        this.controller = controller;
        setValues();
        build();
    }

    public void setValues() {
        setTitle("Visualisierung von Listen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //Schließt Programm, wenn das Fenster schließt
        setSize(1400, 1000);
        setLocationRelativeTo(null);
    }

    public void build() {
        // Hintergrund
        JPanel container = new JPanel();
        container.setBackground(new Color(24, 26, 28));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); //alle container untereinander

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
        buttonPanel.setBackground(container.getBackground());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons einfügen
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); //wie margin in css, abstand zwischen den buttons
        buttonPanel.add(createIconButton("+", "Neue Liste", eventTypes.add)); // button besteht aus Icon+Text, wird per Methode createIconButton(...) gebaut
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen", eventTypes.open));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCE4", "Exportieren", eventTypes.export));

        // Add to container
        container.add(Box.createVerticalGlue());        // flexibler Platzfüller, der überschüssigen Raum verteilt. Alles vertikal mittig
        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(subtitle1);
        container.add(Box.createRigidArea(new Dimension(0, 30))); //fester platzhalter
        container.add(subtitle2);
        container.add(Box.createRigidArea(new Dimension(0, 50)));
        container.add(buttonPanel);
        container.add(Box.createVerticalGlue());

        add(container);
        //Fügt mainPanel in das Fenster (JFrame) ein. mainPanel enthält ganzen Inhalt: Titel, Buttons, Texte
        setVisible(true);
        //Macht das Fenster sichtbar auf dem Bildschirm
    }

    private JPanel createIconButton(String icon, String labelText, eventTypes eventType) {

        CustomIconButton panel = new CustomIconButton(icon, labelText);

        // Hover-Effekt
        panel.addMouseListener(new MouseAdapter() { //Hey Panel, hör ab jetzt auf Mausbewegungen und erstelle einen zuhörer
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case open -> open();
                    case export -> export();
                    case add -> addList();
                }
            }
        });

        return panel;
    }

    private void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String path = System.getProperty("user.dir") + "/src/saves";
        File src = new File(path);

        fileChooser.setCurrentDirectory(src);
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        controller.openList(file);
    }

    private void export() {

    }

    private void addList() {
        String listenName = JOptionPane.showInputDialog(null, "Wie soll die neue Liste heißen?", "Neue Liste erstellen", JOptionPane.PLAIN_MESSAGE);

        if (listenName != null && !listenName.trim().isEmpty()) {
            controller.addList(listenName.trim());
        } else {
            JOptionPane.showMessageDialog(null, "Die Liste braucht einen gültigen Namen.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}