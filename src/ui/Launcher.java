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

    enum eventTypes {add, open}

    public Launcher(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    public void setValues() {
        setTitle("Visualisierung von Listen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);
    }

    public void build() {
        // Hintergrund
        JPanel container = new JPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); //alle container untereinander

        // Titel & Untertitel
        JLabel title = GUIStyle.getStyledLabel("Willkommen bei den friedlichen Koalas", 40);
        JLabel subtitle1 = GUIStyle.getStyledLabel("Hier wird deine Liste visualisiert", 30);
        JLabel subtitle2 = GUIStyle.getStyledLabel("Erstelle oder öffne eine Liste", 22, GUIStyle.getPinkColor());

        // Container für alle Buttons (nebeneinander, zentriert)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(container.getBackground());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons einfügen
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10))); //wie margin in css, abstand zwischen den buttons
        buttonPanel.add(createIconButton("+", "Neue Liste", eventTypes.add)); // button besteht aus Icon+Text, wird per Methode createIconButton(...) gebaut
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen", eventTypes.open));

        // Neues Panel für mittigen Inhalt mit Rahmen
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(container.getBackground());
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GUIStyle.getPinkColor(), 2),  // sichtbarer Rahmen
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));


        centerPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        centerPanel.add(title);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(subtitle1);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        centerPanel.add(subtitle2);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        container.add(Box.createVerticalGlue());
        container.add(centerPanel);
        container.add(Box.createVerticalGlue());

        add(container);
        setVisible(true);
    }

    private JPanel createIconButton(String icon, String labelText, eventTypes eventType) {
        CustomIconButton button = new CustomIconButton(icon, labelText);
        button.setForeground(GUIStyle.getWhiteColor());
        // Set size
        button.setNewSize(220, 110);


        // Actions that happen when button is clicked
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case open -> open();
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

    private void addList() {
        new NewListDialog<>(this, controller); // No listEditor needed
    }
}