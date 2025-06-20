package ui;

import backend.ListEvent;
import backend.ListElement;
import backend.StackManager;
import controls.Controller;
import ui.legos.AddElementPopUp;
import ui.legos.CustomButton;
import ui.legos.DeleteDialog;
import ui.legos.UndoRedoButton;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;

public class ListEditor extends JFrame {

    private final Controller controller;

    private enum eventTypes {backToLauncher, previous, current, next, add, delete}

    private ListElement currentListElement;

    private CustomButton predecessor;
    private CustomButton successor;
    private CustomButton current;

    private File clickSound;
    private File errorSound;

    private UndoRedoButton undoButton;
    private UndoRedoButton redoButton;

    private final StackManager stackManager;

    public ListEditor(Controller controller) {
        this.controller = controller;
        this.stackManager = controller.getStackManager(); // der zentrale StackManager
        setValues();
        build();
        setVisible(true);
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
    public void build() {
        Color backgroundColor = new Color(24, 26, 28);

        // Create container
        JPanel container = new JPanel();
        container.setBackground(backgroundColor);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create layout
        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1};
        editorLayout.rowWeights = new double[]{1, 5, 0, 1};
        container.setLayout(editorLayout);

        // define components
        CustomButton backToLauncher = createButton("← Zurück zum Launcher", 12, eventTypes.backToLauncher);
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
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(new Color(24, 26, 28));

        CustomButton addNodeButton = createButton("Hinzufügen", 24, eventTypes.add);
        CustomButton deleteNodeButton = createButton("Löschen", 24, eventTypes.delete);

        undoButton = new UndoRedoButton("↺", "rückgängig", ListEvent.events.undo);
        redoButton = new UndoRedoButton("↻", "wiederherstellen", ListEvent.events.redo);

        actionPanel.add(undoButton);
        actionPanel.add(addNodeButton);
        actionPanel.add(deleteNodeButton);
        actionPanel.add(redoButton);

        // Add components
        addComponentToGrid(container, backToLauncher, editorLayout, 0, 0, 1, 1, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, predecessor, editorLayout, 0, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout, 2, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout, 0, 2, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, actionPanel, editorLayout, 0, 3, 3, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        add(container);
    }

    private void clickedRemoveNode() {
        DeleteDialog dialog = new DeleteDialog(this);
        dialog.setVisible(true);
    }
    public void onDeleteAll() {
        if (currentListElement != null) {
            ListElement<String> first = currentListElement.getFirst();

            while (first != null) {
                ListElement<String> next = first.getNext();
                first.setPrevious(null);
                first.setNext(null);
                first = next;
            }

            currentListElement = null;
            clearData();
            controller.backToLauncher(null);
        }
    }
    public void onDeleteCurrent() {
        if (currentListElement != null) {
            ListElement vorher = currentListElement.getPrevious();
            ListElement nachher = currentListElement.getNext();

            // Backup für Undo
            ListElement backup = currentListElement.deepCopy();
            System.out.println("[LOG] Backup für Undo erstellt: " + backup.getElement());


            // Event speichern
            stackManager.push(new ListEvent(backup, ListEvent.events.remove));
            System.out.println("[LOG] Event in Stack gespeichert: remove");

            currentListElement.remove();

            if (nachher != null) {
                displayObjet(nachher);
            } else if (vorher != null) {
                displayObjet(vorher);
            } else {
                currentListElement = null;
                clearData();
            }
        }
    }
    public void backToLauncher() {
        controller.backToLauncher(currentListElement);
    }

    public void addNodeAtStart() {
        addNode(AddElementPopUp.positions.atStart);
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

        if (choice < 0) return;

        AddElementPopUp.positions pos = switch (choice) {
            case 0 -> AddElementPopUp.positions.atStart;
            case 2 -> AddElementPopUp.positions.atEnd;
            default -> AddElementPopUp.positions.asNext;
        };
        addNode(pos);
    }

    public void setUndoRedoButtonAvailability(boolean undoAvailability, boolean redoAvailability) {
        undoButton.setAvailable(undoAvailability);
        redoButton.setAvailable(redoAvailability);
    }

    public void addNode(AddElementPopUp.positions position) {
        new AddElementPopUp(currentListElement, position);
    }

    private void addComponentToGrid(Container cont, Component comp, GridBagLayout layout, int x, int y, int width, int height, int fill, Insets padding, int anchor) {
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
        // Add padding
        button.setBorder(new EmptyBorder(5,5,5,5));

        // Set size
        button.setNewSize(160,80);

        button.addActionListener(e -> {
            switch (eventType) {
                case backToLauncher -> controller.backToLauncher(currentListElement);
                case next -> displayObjet(currentListElement.getNext());
                case previous -> displayObjet(currentListElement.getPrevious());
                case add -> clickedAddNode();
                case delete -> clickedRemoveNode();
            }
        });
        return button;
    }

    public void openList(ListElement currentListElement) {
        this.currentListElement = currentListElement;
        setData(currentListElement);
    }

    private void setData(ListElement newData) {
        System.out.println("[LOG] setData() aufgerufen mit: " + (newData != null ? newData.getElement() : "null"));

        if (newData == null) {
            clearData();
            currentListElement = null;
            System.out.println("[LOG] Kein Element vorhanden – Anzeige geleert.");
            return;
        }

        currentListElement = newData;
        String[] readableData = newData.getData();

        System.out.println("[LOG] Anzeige wird aktualisiert:");
        System.out.println("       Vorgänger: " + readableData[0]);
        System.out.println("       Aktuell:   " + readableData[1]);
        System.out.println("       Nachfolger:" + readableData[2]);

        predecessor.setText(readableData[0]);
        current.setText(readableData[1]);
        successor.setText(readableData[2]);

        if (!isVisible()) {
            System.out.println("[LOG] Fenster war unsichtbar – wird sichtbar gemacht");
            setVisible(true);
        }

        revalidate();
        repaint();
    }

    private void displayObjet(ListElement newObject) {
        if (newObject != null) {
            controller.playSound(clickSound);
            setData(newObject);
        } else {
            controller.playSound(errorSound);
        }
    }

    public ListElement getCurrentListElement() {
        return currentListElement;
    }

    public void clearData() {
        predecessor.setText("");
        current.setText("");
        successor.setText("");
    }
}