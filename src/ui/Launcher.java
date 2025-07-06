package ui;

import controls.Controller;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import ui.dialogs.NewListDialog;
import ui.legos.CustomIconButton;
import ui.style.GUIStyle;

public class Launcher<T> extends JFrame {

    private final Controller<T> controller;

    private JPanel centerPanel;

    public void setCentralPanelTheme() {
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GUIStyle.getHighlightedColor(), 2),  // sichtbarer Rahmen
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
    }

    enum eventTypes {add, open}

    public Launcher(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    public void setValues() {
        setTitle("Visualisierung von Listen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);
        setIconImage(GUIStyle.getWindowIcon());
    }

    public void build() {
        // Hintergrund
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(GUIStyle.getBackgroundColor());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); //alle container untereinander

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        topRightPanel.setBackground(GUIStyle.getBackgroundColor());

        ImageIcon moonLight = getScaledIcon("/assets/moon_light.png", 25, 25);
        ImageIcon moonDark = getScaledIcon("/assets/moon_dark.png", 25, 25);

        JToggleButton toggleButton = new JToggleButton();
        toggleButton.setIcon(moonLight);
        toggleButton.setSelectedIcon(moonDark);

        toggleButton.setToolTipText("Darstellungsmodus wechseln");
        toggleButton.setPreferredSize(new Dimension(45, 35));
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        toggleButton.addItemListener(e -> {
            controller.toggleTheme();
            controller.playSound(GUIStyle.getClickSoundFile());
        });

        topRightPanel.add(toggleButton);
        container.add(topRightPanel, BorderLayout.NORTH);

        // Titel & Untertitel
        JLabel title = GUIStyle.getStyledLabel("Willkommen bei den friedlichen Koalas", 40);
        JLabel subtitle1 = GUIStyle.getStyledLabel("Hier wird deine Liste visualisiert", 30);
        JLabel subtitle2 = GUIStyle.getStyledLabel("Erstelle oder öffne eine Liste", 22, GUIStyle.getHighlightedColor());

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
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(container.getBackground());
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setCentralPanelTheme();


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
        button.setForeground(GUIStyle.getFontColor());
        // Set size
        button.setNewSize(220, 110);


        // Actions that happen when button is clicked
        button.addActionListener(e -> {
                controller.playSound(GUIStyle.getClickSoundFile());
                switch (eventType) {
                    case open -> open();
                    case add -> addList();
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
        if(fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION){
            return;
        }
        File file = fileChooser.getSelectedFile();

        // Prevent NullPointerException, if no file is selected
        if (file != null) {
            controller.initializeStacks();
            controller.openList(file);
        }
    }
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void addList() {
        new NewListDialog<>(this, controller); // No listEditor needed
    }
}