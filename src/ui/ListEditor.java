package ui;

import backend.CustomObject;
import controls.Controller;
import ui.legos.CustomButton;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class ListEditor extends JFrame{

    private final Controller controller;
    private enum eventTypes {backToLauncher, previous, current, next, add, delete}
    private CustomObject anker;

    private CustomButton predecessor;
    private CustomButton successor;
    private CustomButton current;

    public ListEditor(Controller controller) {
        this.controller = controller;
        setValues();
        build();
    }

    private void setValues() {
        setTitle("List-Editor");
        setVisible(true);
        setSize(1080, 720);
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void build(){
        Color backgroundColor = new Color(24, 26 ,28);

        // Create container
        JPanel container = new JPanel();
        container.setBackground(backgroundColor);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create layout
        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1};
        editorLayout.rowWeights = new double[]{1, 5, 0, 1};
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

        // SubPanel for the add and delete button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(Box.createHorizontalStrut(50));

        // Add and delete button
        CustomButton addNodeButton = createButton("Hinzufügen", 24, eventTypes.add);
        buttonPanel.add(addNodeButton);
        CustomButton deleteNodeButton = createButton("Löschen", 24, eventTypes.delete);
        buttonPanel.add(deleteNodeButton);

        // Add components to container
        addComponentToGrid(container, backToLauncher, editorLayout, 0, 0, 1, 1, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, predecessor, editorLayout,    0, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(container, successor, editorLayout,      2, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(container, current, editorLayout,        1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, index, editorLayout,          0, 2, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(container, buttonPanel, editorLayout, 0, 3, 3, 1, GridBagConstraints.NORTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        add(container);
    }

    private void addComponentToGrid(Container cont, Component comp, GridBagLayout layout, int x, int y, int width, int height, int fill, Insets padding, int anchor){
        /*
        This method adds the components "comp" to the container "cont" with specified attributes
         */

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
                    case backToLauncher -> controller.backToLauncher(anker);
                    case next -> displayNext();
                    case previous -> displayPrevious();
                    // TODO: Implement different methods
                }
            }
        });
        return button;
    }

    public void openList(CustomObject anker) {
        this.anker = anker;
        setData(anker);
    }

    private void setData(CustomObject currentData) {
        String[] readableData = currentData.getData();
        predecessor.setText(readableData[0]);
        current.setText(readableData[1]);
        successor.setText(readableData[2]);
    }

    private void displayNext() {
        if (anker.getNext() != null) {
            openList(anker.getNext());
        }
        else {
            Controller.handleError("Es gibt keinen weiteren Nachfolger");
        }
    }

    private void displayPrevious() {
        if (anker.getPrevious() != null) {
            openList(anker.getPrevious());
        }
        else {
            Controller.handleError("Es gibt keinen weiteren Vorgänger");
        }
    }
}