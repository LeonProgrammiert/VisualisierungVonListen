package ui;
import controls.Controller;
import ui.legos.CustomIconButton;

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
        createUI();
    }

    public void createUI() {
        setTitle("Visualisierung von Listen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //Schließt Programm, wenn das Fenster schließt
        setSize(2000, 1600);

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
        buttonPanel.add(createIconButton("+", "Neue Liste", eventTypes.add)); // button besteht aus Icon+Text, wird per Methode createIconButton(...) gebaut
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen", eventTypes.open));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCE4", "Exportieren", eventTypes.export));

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

        add(mainPanel);
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

    }
}