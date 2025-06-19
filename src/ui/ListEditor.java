package ui;

import backend.Event;
import backend.ListElement;
import controls.Controller;
import ui.legos.AddElementPopUp;
import ui.legos.CustomButton;
import ui.legos.UndoRedoButton;


import java.awt.*;
import javax.swing.*;
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

    public ListEditor(Controller controller) {
        this.controller = controller;
        setValues();
        build();
    }

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

    public void build() {
        Color backgroundColor = new Color(24, 26, 28);

        JPanel container = new JPanel();
        container.setBackground(backgroundColor);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1};
        editorLayout.rowWeights = new double[]{1, 5, 0, 1};
        container.setLayout(editorLayout);

        // define components
        CustomButton backToLauncher = createButton("\u2190 Zurück zum Launcher", 12, eventTypes.backToLauncher);
        predecessor = createButton("Vorgänger", 24, eventTypes.previous);
        successor = createButton("Nachfolger", 24, eventTypes.next);
        current = createButton("Aktuell", 24, eventTypes.current);

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

        undoButton = new UndoRedoButton("↺", "rückgängig", Event.events.undo);
        undoRedoPanel.add(undoButton);
        redoButton = new UndoRedoButton("↻", "wiederherstellen", Event.events.redo);
        undoRedoPanel.add(redoButton);

        actionPanel.add(buttonPanel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(undoRedoPanel);


        // Add components
        addComponentToGrid(container, backToLauncher, editorLayout, 0, 0, 1, 1, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, predecessor, editorLayout, 0, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout, 2, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout, 1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout, 0, 2, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
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

    private void addNode(AddElementPopUp.positions position) {
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

    private CustomButton createButton(String buttonText, int fontSize, eventTypes eventType) {
        CustomButton button = new CustomButton(buttonText, fontSize);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case backToLauncher -> controller.backToLauncher(currentListElement);
                    case next -> displayObjet(currentListElement.getNext());
                    case previous -> displayObjet(currentListElement.getPrevious());
                    case add -> clickedAddNode();
                    case delete -> clickedRemoveNode();
                }
            }
        });
        return button;
    }

    public void openList(ListElement currentListElement) {
        this.currentListElement = currentListElement;
        setData(currentListElement);
    }

    private void setData(ListElement newData) {
        this.currentListElement = newData;
        if (currentListElement != null) {
            String[] readableData = newData.getData();
            predecessor.setText(readableData[0]);
            current.setText(readableData[1]);
            successor.setText(readableData[2]);
            setVisible(true);
        } else {
            addNode(AddElementPopUp.positions.firstElement);
        }
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
}