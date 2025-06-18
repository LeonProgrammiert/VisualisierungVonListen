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

    enum eventTypes {ADD, OPEN, EXPORT}

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
        JPanel container = new JPanel();
        container.setBackground(new Color(24, 26, 28));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(Box.createVerticalGlue());
        container.add(buildTitlePanel());
        container.add(Box.createRigidArea(new Dimension(0, 50)));
        container.add(buildButtonPanel());
        container.add(Box.createVerticalGlue());

        add(container);
        setVisible(true);
    }

    private JPanel buildTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(24, 26, 28));

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

        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(subtitle1);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        titlePanel.add(subtitle2);

        return titlePanel;
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(24, 26, 28));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("+", "Neue Liste", eventTypes.ADD));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCC2", "Öffnen", eventTypes.OPEN));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(createIconButton("\uD83D\uDCE4", "Exportieren", eventTypes.EXPORT));

        return buttonPanel;
    }

    private JPanel createIconButton(String icon, String labelText, eventTypes eventType) {
        CustomIconButton panel = new CustomIconButton(icon, labelText);

        // Add actions when button is clicked
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case OPEN -> open();
                    case EXPORT -> export();
                    case ADD -> addList();
                }
            }
        });
        return panel;
    }

    private void open() {
        // Configure file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/saves"));
        fileChooser.showOpenDialog(this);
        // Get selected file
        File file = fileChooser.getSelectedFile();
        // Open file
        controller.openList(file);
    }

    private void export() {

    }

    private void addList() {
        // Configure OptionPane
        String listenName = JOptionPane.showInputDialog(
                null,
                "Wie soll die neue Liste heißen?",
                "Neue Liste erstellen",
                JOptionPane.PLAIN_MESSAGE
        );

        // User cancelled or closed the dialog – do nothing
        if (listenName == null) {
            return;
        }

        // Mange inputs
        listenName = listenName.trim();
        if (!listenName.isEmpty()) {
            controller.addList(listenName);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Die Liste braucht einen gültigen Namen.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}