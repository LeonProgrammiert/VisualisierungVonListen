package ui;

import backend.CustomObject;
import controls.Controller;
import ui.legos.AddNode;
import ui.legos.CustomButton;
import ui.legos.UndoRedoButton;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;

public class ListEditor extends JFrame{

    private final Controller controller;
    private enum eventTypes {backToLauncher, previous, current, next, add, delete}
    private CustomObject anker;

    private CustomButton predecessor;
    private CustomButton successor;
    private CustomButton current;

    private File clickSound;
    private File errorSound;

    public ListEditor(Controller controller) {
        this.controller = controller;
        setValues();
        build();
    }

    //stellt die Grundeinstellungen des Fensters ein
    private void setValues() {
        setTitle("List-Editor");
        setVisible(true);
        setSize(1400, 1000);
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.out.println(System.getProperty("user.dir"));

        clickSound = new File(System.getProperty("user.dir") + "/src/assets/clickSound.wav");
        errorSound = new File(System.getProperty("user.dir") + "/src/assets/errorSound.wav");
    }

    //baut die Benutzeroberfläche
    public void build(){
        Color backgroundColor = new Color(24, 26 ,28);

        // Create container
        JPanel container = new JPanel();
        container.setBackground(backgroundColor);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create layout
        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1}; //3 spalten 1:2:1
        editorLayout.rowWeights = new double[]{1, 5, 0, 1}; // 4 zeilen mit gewichtung
        container.setLayout(editorLayout);

        // Define components
        CustomButton backToLauncher = createButton("\u2190 Zurück zum Launcher", 12, eventTypes.backToLauncher);
        predecessor = createButton("Vorgänger", 24, eventTypes.previous);
        successor = createButton("Nachfolger", 24, eventTypes.next);
        current = createButton("Aktuell", 24, eventTypes.current);

        // Label for index of te list
        JLabel index = new JLabel("Indexplatzhalter");
        index.setHorizontalAlignment(JLabel.CENTER);
        index.setForeground(Color.WHITE);
        index.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        // Gemeinsames Panel für Hinzufügen/Löschen + Undo/Redo
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBackground(new Color(24, 26, 28));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(24, 26, 28));

        CustomButton addNodeButton = createButton("Hinzufügen", 24, eventTypes.add);
        CustomButton deleteNodeButton = createButton("Löschen", 24, eventTypes.delete);

        buttonPanel.add(addNodeButton);
        buttonPanel.add(deleteNodeButton);

        JPanel undoRedoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        undoRedoPanel.setBackground(new Color(24, 26, 28));
        undoRedoPanel.add(new UndoRedoButton("↺", "Undo changes"));
        undoRedoPanel.add(new UndoRedoButton("↻", "Redo changes"));

        actionPanel.add(buttonPanel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(undoRedoPanel);


        // Add components
        addComponentToGrid(container, backToLauncher, editorLayout, 0, 0, 1, 1, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, predecessor, editorLayout,    0, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout,      2, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout,        1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout,          0, 2, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, actionPanel, editorLayout, 0, 3, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        add(container);
    }

    private void clickedRemoveNode() {
        // TODO: implement
    }

    private void clickedAddNode() {
        String[] options = {"Start", "Nächster", "Ende"};

        // Show the option dialog
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose one of the following options:",
                "Custom Options Dialog",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle the user's choice
        if (choice >= 0) addNode(choice);
    }

    private void addNode(int position) {
        new AddNode(anker, position);
    }

    private void addComponentToGrid(Container cont, Component comp, GridBagLayout layout, int x, int y, int width, int height, int fill, Insets padding, int anchor){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = padding;
        gbc.anchor = anchor;
        layout.setConstraints(comp, gbc);
        cont.add(comp);
    }

    //erstellt Button und verknüpft ihn mit der richtigen Funktion
    private CustomButton createButton(String buttonText, int fontSize, eventTypes eventType) {
        CustomButton button = new CustomButton(buttonText, fontSize);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case backToLauncher -> controller.backToLauncher(anker);
                    case next -> displayObjet(anker.getNext());
                    case previous -> displayObjet(anker.getPrevious());
                    case add -> clickedAddNode();
                    case delete -> clickedRemoveNode();
                }
            }});
        return button;
    }


    //speichert den aktuellen Knoten der Liste und zeigt auch sofort im gui an
    public void openList(CustomObject anker) {
        this.anker = anker;
        setData(anker);
    }

    //holt Vorgänger, Aktuell, Nachfolger aus dem Knoten und zeigt sie auf den Buttons an.
    private void setData(CustomObject currentData) {
        if (anker != null) {
            String[] readableData = currentData.getData();
            predecessor.setText(readableData[0]);
            current.setText(readableData[1]);
            successor.setText(readableData[2]);
            setVisible(true);
        }
        else {
            // -10 means it's a new list
            addNode(-10);
        }
    }

    private void displayObjet(CustomObject newObject) {
        if (newObject != null) {
            controller.playSound(clickSound);
            openList(newObject);
        } else {
            controller.playSound(errorSound);
        }
    }
}