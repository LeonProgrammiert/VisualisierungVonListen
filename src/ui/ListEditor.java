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

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1};
        editorLayout.rowWeights = new double[]{1, 5, 0, 1};
        mainPanel.setLayout(editorLayout);

        // define components
        CustomButton backToLauncher = createButton("\u2190 Zurück zum Launcher", 12, eventTypes.backToLauncher);
        CustomButton predecessor = createButton("Vorgänger", 24, eventTypes.previous);
        CustomButton successor = createButton("Nachfolger", 24, eventTypes.next);
        CustomButton current = createButton("Aktuell", 24, eventTypes.current);

        JLabel index = new JLabel("Indexplatzhalter");
        index.setHorizontalAlignment(JLabel.CENTER);
        index.setForeground(Color.WHITE);
        index.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        // SubPanel for grouping the add and delete button
        JPanel addDeletePanel = new JPanel();
        addDeletePanel.setBackground(backgroundColor);
        addDeletePanel.setLayout(new FlowLayout());

        CustomButton addNodeButton = createButton("Hinzufügen", 24, eventTypes.add);
        addDeletePanel.add(addNodeButton);

        addDeletePanel.add(Box.createHorizontalStrut(50));

        CustomButton deleteNodeButton = createButton("Löschen", 24, eventTypes.delete);
        addDeletePanel.add(deleteNodeButton);

        // add components
        addComponentToGrid(mainPanel, backToLauncher, editorLayout, 0, 0, 1, 1, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), GridBagConstraints.NORTHWEST);
        addComponentToGrid(mainPanel, predecessor, editorLayout,    0, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 60, 60, 30), GridBagConstraints.NORTHWEST);
        addComponentToGrid(mainPanel, successor, editorLayout,      2, 1, 1, 1, GridBagConstraints.BOTH, new Insets(60, 30, 60, 60), GridBagConstraints.CENTER);
        addComponentToGrid(mainPanel, current, editorLayout,        1, 1, 1, 1, GridBagConstraints.BOTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(mainPanel, index, editorLayout,          0, 2, 3, 1, GridBagConstraints.NONE, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);
        addComponentToGrid(mainPanel, addDeletePanel, editorLayout, 0, 3, 3, 1, GridBagConstraints.NORTH, new Insets(30, 30, 30, 30), GridBagConstraints.CENTER);

        add(mainPanel);
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

    private CustomButton createButton(String buttonText, int fontSize, eventTypes eventType) {
        CustomButton button = new CustomButton(buttonText, fontSize);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                switch (eventType) {
                    case backToLauncher -> controller.backToLauncher(anker);
                    // TODO: Implement different methods
                }
            }
        });
        return button;
    }

    public void openList(CustomObject anker) {
        this.anker = anker;
    }

}
