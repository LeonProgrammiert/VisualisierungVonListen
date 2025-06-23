package ui;

import ui.dialogs.NewListDialog;
import ui.legos.CustomIconButton;
import controls.Controller;
import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Launcher<T> extends JFrame {

    private final Controller<T> controller;

    enum eventTypes {add, open, export}

    public Launcher(Controller<T> controller) {
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
        container.setBackground(GUIStyle.getGrayColor());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); //alle container untereinander

        // Titel & Untertitel
        JLabel title = GUIStyle.getStyledLabel("Willkommen bei den friedlichen Koalas", 24);
        JLabel subtitle1 = GUIStyle.getStyledLabel("Hier wird deine Liste visualisiert", 19);
        JLabel subtitle2 = GUIStyle.getStyledLabel("Erstelle oder öffne eine Liste", 14, GUIStyle.getPinkColor());

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

        //Fügt mainPanel in das Fenster (JFrame) ein. mainPanel enthält ganzen Inhalt: Titel, Buttons, Texte
        add(container);
        //Macht das Fenster sichtbar auf dem Bildschirm
        setVisible(true);
    }

    private JPanel createIconButton(String icon, String labelText, eventTypes eventType) {
        CustomIconButton button = new CustomIconButton(icon, labelText);
        button.setForeground(GUIStyle.getWhiteColor());
        // Set size
        button.setNewSize(160, 80);

        // Actions that happen when button is clicked
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case open -> open();
                    case export -> export();
                    case add -> addList();
                }
            }
        });
        return button;
    }

    private void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String path = System.getProperty("user.dir") + "/src/saves";
        File src = new File(path);

        fileChooser.setCurrentDirectory(src);
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();

        // Prevent NullPointerException, if no file is selected
        if (file != null) {
            controller.initializeStacks();
            controller.openList(file);
        }
    }

    private void export() {

    }

    private void addList() {
        new NewListDialog<>(this, controller); // No listEditor needed
    }
}