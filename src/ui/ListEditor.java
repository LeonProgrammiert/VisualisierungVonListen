package ui;

import backend.enumerations.AddElementPositions;
import ui.dialogs.SaveDiscardDialog;
import ui.dialogs.DeleteDialog;
import ui.legos.UndoRedoButton;
import ui.legos.CustomButton;
import backend.ListUtilities;
import ui.dialogs.AddDialog;
import ui.legos.SaveButton;
import backend.ListElement;
import controls.Controller;
import backend.ListEvent;
import ui.style.GUIStyle;

import javax.swing.*;
import java.io.File;
import java.awt.*;

public class ListEditor <T> extends JFrame {

    private final Controller<T> controller;

    private enum eventTypes {backToLauncher, previous, next, add, delete, saveList, viewList}

    private ListElement<T> currentListElement;

    private CustomButton predecessor;
    private CustomButton successor;
    private CustomButton current;
    private SaveButton saveListButton;
    private JLabel index;

    private File clickSound;
    private File errorSound;

    private UndoRedoButton undoButton;
    private UndoRedoButton redoButton;

    public ListEditor(Controller<T> controller) {
        this.controller = controller;
        setVisible(false);

        setValues();
        build();
    }

    //stellt die Grundeinstellungen des Fensters ein
    private void setValues() {
        setTitle("List-Editor");
        setSize(GUIStyle.getFrameSize());
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.out.println(System.getProperty("user.dir"));

        clickSound = new File(System.getProperty("user.dir") + "/src/assets/clickSound.wav");
        errorSound = new File(System.getProperty("user.dir") + "/src/assets/errorSound.wav");
    }

    //baut die Benutzeroberfläche
    public void build() {
        Color backgroundColor = GUIStyle.getBackgroundColor();

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
        int fontsize = 18;
        CustomButton backToLauncher = createButton("← Zurück zum Launcher", fontsize, eventTypes.backToLauncher);
        CustomButton listViewButton = createButton("Liste anzeigen", fontsize, eventTypes.viewList);
        saveListButton = new SaveButton(fontsize);

        fontsize = 24;
        predecessor = createButton("Vorgänger", fontsize, eventTypes.previous);
        successor = createButton("Nachfolger", fontsize, eventTypes.next);
        current = createButton("Aktuell", fontsize, null);

        current.setNewSize(100, 160);

        Dimension size = new Dimension(275, 60);
        backToLauncher.setNewSize(size);
        listViewButton.setNewSize(size);
        saveListButton.setNewSize(size);


        // Label for index of the list
        index = GUIStyle.getStyledLabel("Indexplatzhalter", fontsize);


        // Gemeinsames Panel für Hinzufügen/Löschen + Undo/Redo
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(new Color(24, 26, 28));
        actionPanel.setOpaque(false);

        CustomButton addNodeButton = createButton("Hinzufügen", fontsize, eventTypes.add);
        CustomButton deleteNodeButton = createButton("Löschen", fontsize, eventTypes.delete);

        size = new Dimension(210, 60);
        addNodeButton.setNewSize(size);
        deleteNodeButton.setNewSize(size);

        undoButton = new UndoRedoButton("↺", "rückgängig", ListEvent.events.undo);
        redoButton = new UndoRedoButton("↻", "wiederherstellen", ListEvent.events.redo);


        // Add buttons to actionPanel
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
        int margin = 15;
        addComponentToGrid(centerPanel, backToLauncher, editorLayout, 0, 0, 1, GridBagConstraints.NONE, null, GridBagConstraints.NORTHWEST);
        centerPanel.add(Box.createRigidArea(new Dimension(margin, 0)));
        addComponentToGrid(centerPanel, listViewButton, editorLayout, 1,0,1, GridBagConstraints.NONE, null, GridBagConstraints.NORTH);
        centerPanel.add(Box.createRigidArea(new Dimension(margin, 0)));
        addComponentToGrid(centerPanel, saveListButton, editorLayout, 2, 0, 1, GridBagConstraints.NONE, null, GridBagConstraints.NORTHEAST);

        addComponentToGrid(container, predecessor, editorLayout, 0, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout, 2, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout, 0, 2, 3, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, actionPanel, editorLayout, 0, 3, 3, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        addComponentToGrid(container, centerPanel, editorLayout, 0, 0, 3, GridBagConstraints.NONE, new Insets(0, 0, 30, 0), GridBagConstraints.CENTER);
        setContentPane(container);
    }

    private void clickedRemoveNode() {
        DeleteDialog<T> dialog = new DeleteDialog<>(this, this, controller);
        dialog.setVisible(true);
    }

    public void backToLauncher() {
        if (Controller.unsavedChanges) {
            new SaveDiscardDialog<>(this, controller);
        } else {
            controller.backToLauncher();
        }
    }

    private void clickedAddNode() {
        new AddDialog<>(this, this, currentListElement, true);
    }

    public void setUndoRedoButtonAvailability(boolean undoAvailability, boolean redoAvailability) {
        undoButton.setAvailable(undoAvailability);
        redoButton.setAvailable(redoAvailability);
        Controller.unsavedChanges = undoAvailability || redoAvailability;
        updateSaveAvailability(Controller.unsavedChanges);
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

    private CustomButton createButton(String buttonText, int fontSize, eventTypes eventType) {
        // erstellt Button und verknüpft ihn mit der richtigen Funktion
        CustomButton button = new CustomButton(buttonText, fontSize);

        // Set size
        button.setNewSize(160, 80);

        // Add click action
        button.addActionListener(e -> {
            if (eventType != eventTypes.next && eventType != eventTypes.previous) {
                controller.playSound(GUIStyle.getClickSoundFile());
            }

            switch (eventType) {
                case null -> {}
                case backToLauncher -> backToLauncher();
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
        updateSaveAvailability(false);
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

        // Clear data if newData is null
        if (newData == null) {
            clearData();
            currentListElement = null;
            System.out.println("[LOG] Kein Element vorhanden – Anzeige geleert.");
            return;
        }

        // Overwrite current
        currentListElement = newData;

        // Get data
        String[] readableData = ListUtilities.getData(newData);
        String indexString = newData.getIndex() + "/" + newData.getMaxIndex();

        // LOG
        System.out.println("[LOG] Anzeige wird aktualisiert:");
        System.out.println("       Vorgänger: " + readableData[0]);
        System.out.println("       Aktuell:   " + readableData[1]);
        System.out.println("       Nachfolger:" + readableData[2]);
        System.out.println("       Index: "     +   indexString);


        // Set data
        predecessor.setText(readableData[0]);
        current.setText(readableData[1]);
        successor.setText(readableData[2]);
        index.setText(indexString);

        predecessor.setEnabled(true);
        current.setEnabled(true);
        successor.setEnabled(true);
    }

    private void displayObject(ListElement<T> newObject) {
        if (newObject != null) {
            controller.playSound(GUIStyle.getSwapSoundFile());
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

    public void updateSaveAvailability(boolean isUnsaved) {
        Controller.unsavedChanges = isUnsaved;
        saveListButton.updateSaveAvailability(Controller.unsavedChanges);
    }
}