package ui;

import backend.enumerations.AddElementPositions;
import ui.dialogs.DeleteDialog;
import ui.dialogs.SaveDiscardDialog;
import ui.legos.UndoRedoButton;
import ui.legos.CustomButton;
import ui.dialogs.AddDialog;
import backend.ListElement;
import controls.Controller;
import backend.ListEvent;
import ui.style.GUIStyle;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.io.File;
import java.awt.*;

public class ListEditor<T> extends JFrame {

    private final Controller<T> controller;

    private enum eventTypes {backToLauncher, previous, current, next, add, delete, saveList, viewList}

    private ListElement<T> currentListElement;

    private CustomButton predecessor;
    private CustomButton successor;
    private CustomButton current;
    private CustomButton saveListButton;

    private File clickSound;
    private File errorSound;

    private UndoRedoButton undoButton;
    private UndoRedoButton redoButton;

    private boolean listHasBeenEdited;

    public ListEditor(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
        setVisible(true);
    }

    //stellt die Grundeinstellungen des Fensters ein
    private void setValues() {
        setTitle("List-Editor");
        setVisible(true);
        setSize(GUIStyle.getFrameSize());
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.out.println(System.getProperty("user.dir"));

        clickSound = new File(System.getProperty("user.dir") + "/src/assets/clickSound.wav");
        errorSound = new File(System.getProperty("user.dir") + "/src/assets/errorSound.wav");

        listHasBeenEdited = false;
    }

    //baut die Benutzeroberfläche
    public void build() {
        Color backgroundColor = GUIStyle.getGrayColor();

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
        CustomButton backToLauncher = createButton("← Zurück zum Launcher", 14, eventTypes.backToLauncher);
        CustomButton listViewButton = createButton("Liste anzeigen", 14, eventTypes.viewList);
        saveListButton = createButton("🖫 Liste speichern", 14, eventTypes.saveList);
        predecessor = createButton("Vorgänger", 24, eventTypes.previous);
        successor = createButton("Nachfolger", 24, eventTypes.next);
        current = createButton("Aktuell", 24, eventTypes.current);

        current.setMaximumSize(new Dimension(100, 160));
        current.setPreferredSize(new Dimension(100, 160));
        backToLauncher.setNewSize(250, 30);
        listViewButton.setNewSize(250, 30);
        saveListButton.setNewSize(250,30);


        // Label for index of te list
        JLabel index = GUIStyle.getStyledLabel("Indexplatzhalter", 24);

        // Gemeinsames Panel für Hinzufügen/Löschen + Undo/Redo
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(new Color(24, 26, 28));
        actionPanel.setOpaque(false);

        CustomButton addNodeButton = createButton("Hinzufügen", 24, eventTypes.add);
        CustomButton deleteNodeButton = createButton("Löschen", 24, eventTypes.delete);

        undoButton = new UndoRedoButton("↺", "rückgängig", ListEvent.events.undo);
        redoButton = new UndoRedoButton("↻", "wiederherstellen", ListEvent.events.redo);

        actionPanel.add(undoButton);
        actionPanel.add(addNodeButton);
        actionPanel.add(deleteNodeButton);
        actionPanel.add(redoButton);

        //Panel für zentrale Leiste
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(container.getBackground());
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Add components
        addComponentToGrid(centerPanel, backToLauncher, editorLayout, 0, 0, 1, GridBagConstraints.NONE, null, GridBagConstraints.NORTHWEST);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        addComponentToGrid(centerPanel, listViewButton, editorLayout, 1,0,1, GridBagConstraints.NONE, null, GridBagConstraints.NORTH);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        addComponentToGrid(centerPanel, saveListButton, editorLayout, 2, 0, 1, GridBagConstraints.NONE, null, GridBagConstraints.NORTHEAST);

        addComponentToGrid(container, predecessor, editorLayout, 0, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout, 2, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout, 0, 2, 3, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, actionPanel, editorLayout, 0, 3, 3, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        addComponentToGrid(container, centerPanel, editorLayout, 0, 0, 3, GridBagConstraints.NONE, new Insets(0, 0, 30, 0), GridBagConstraints.CENTER);
        add(container);
    }

    private void clickedRemoveNode() {
        DeleteDialog<T> dialog = new DeleteDialog<>(this, this, controller);
        dialog.setVisible(true);

    }

    public void backToLauncher() {
        if (listHasBeenEdited) {
            new SaveDiscardDialog<>(this);
        }
        controller.backToLauncher();
    }

    private void clickedAddNode() {
        new AddDialog<>(this, this, currentListElement, true);
    }

    public void setUndoRedoButtonAvailability(boolean undoAvailability, boolean redoAvailability) {
        undoButton.setAvailable(undoAvailability);
        redoButton.setAvailable(redoAvailability);
        listHasBeenEdited = undoAvailability;
        updateSaveAvailability();
    }

    private void addComponentToGrid(Container cont, Component comp, GridBagLayout layout, int x, int y, int width, int fill, Insets padding, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = 1;

        // Insets are already set to 0 in the gbc-Constructor
        if (padding != null) {
            gbc.insets = padding;
        }

        gbc.anchor = anchor;
        layout.setConstraints(comp, gbc);
        cont.add(comp);
    }

    // erstellt Button und verknüpft ihn mit der richtigen Funktion
    private CustomButton createButton(String buttonText, int fontSize, eventTypes eventType) {
        CustomButton button = new CustomButton(buttonText, fontSize);
        // Add padding
        button.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Set size
        button.setNewSize(160, 80);

        // Add click action
        button.addActionListener(e -> {
            switch (eventType) {
                case backToLauncher -> backToLauncher();
                case current -> {
                } // to be implemented
                case next -> displayObject(currentListElement.getNext());
                case previous -> displayObject(currentListElement.getPrevious());
                case add -> clickedAddNode();
                case delete -> clickedRemoveNode();
                case saveList -> saveList();
                case viewList -> controller.openListView(currentListElement);
            }
        });
        return button;
    }

    public void saveList() {
        controller.saveList(currentListElement.getFirst());

        // Update saving availability
        listHasBeenEdited = false;
        updateSaveAvailability();
    }

    public void openList(ListElement<T> currentListElement) {
        this.currentListElement = currentListElement;
        if (currentListElement == null) {
            new AddDialog<>(this, this, null, AddElementPositions.firstElement);
        } else {
            setData(currentListElement);
        }
    }

    private void setData(ListElement<T> newData) {
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

        predecessor.setEnabled(true);
        current.setEnabled(true);
        successor.setEnabled(true);

        if (!isVisible()) {
            System.out.println("[LOG] Fenster war unsichtbar – wird sichtbar gemacht");
            this.setVisible(true);
        }

        revalidate();
        repaint();
    }

    private void displayObject(ListElement<T> newObject) {
        if (newObject != null) {
            controller.playSound(clickSound);
            setData(newObject);
        } else {
            controller.playSound(errorSound);
        }
    }

    public ListElement<T> getCurrentListElement() {
        return currentListElement;
    }

    public void clearData() {
        predecessor.setText("N/A");
        current.setText("N/A");
        successor.setText("N/A");

        predecessor.setEnabled(false);
        current.setEnabled(false);
        successor.setEnabled(false);
    }

    public void updateSaveAvailability() {
        if (listHasBeenEdited) {
            saveListButton.setText("🖫* Liste speichern");
        } else {
            saveListButton.setText("🖫 Liste speichern");
        }
    }

    public void markListAsEdited() {
        listHasBeenEdited = true;
        updateSaveAvailability();
    }

}