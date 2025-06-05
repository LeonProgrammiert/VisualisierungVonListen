package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

// TO-DO: 
// - undo/redo buttons and/or keyboard shortcut
// - button functionality

public class ListEditor extends JFrame{
    
    public static void main(String[] args) {
        ListEditor frame = new ListEditor();
    }
    
    public ListEditor(){
        createUI();
    }
    
    public void createUI(){

        setTitle("List-Editor");
        setVisible(true);
        setSize(1080, 720);
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color backgroundColor = new Color(24, 26 ,28);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagLayout editorLayout = new GridBagLayout();
        editorLayout.columnWeights = new double[]{1, 2, 1};
        editorLayout.rowWeights = new double[]{1, 5, 0, 1};
        mainPanel.setLayout(editorLayout);

        // define components
        JButton backToLauncher = createButton("Zurück zum Launcher", 12, "\u2190");
        JButton predecessor = createButton("Vorgänger", 24, null);
        JButton successor = createButton("Nachfolger", 24, null);        
        JButton current = createButton("Aktuell", 24, null);

        JLabel index = new JLabel("Indexplatzhalter");
        index.setHorizontalAlignment(JLabel.CENTER);
        index.setForeground(Color.WHITE);
        index.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        // SubPanel for grouping the add and delete button
        JPanel addDeletePanel = new JPanel();
        addDeletePanel.setBackground(backgroundColor);
        addDeletePanel.setLayout(new FlowLayout());
        
        JButton addNodeButton = createButton("Hinzufügen", 24, null);
        addDeletePanel.add(addNodeButton);

        addDeletePanel.add(Box.createHorizontalStrut(50));

        JButton deleteNodeButton = createButton("Löschen", 24, null);
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

    private JButton createButton(String buttonText, int fontSize, String unicodeIcon) {

        JButton button;
        
        if(unicodeIcon != null){
            button = new JButton(unicodeIcon + " " + buttonText);
        }
        else{
            button = new JButton(buttonText);
        }

        button.setBackground(new Color(40, 40, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2));
        button.setOpaque(true);
        button.setFocusable(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        button.setForeground(Color.LIGHT_GRAY);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(65, 65, 65));
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 2));
                button.setForeground(new Color(255, 182, 193));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(40, 40, 40));
                button.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2));
                button.setForeground(Color.LIGHT_GRAY);
            }

            public void mousePressed(MouseEvent e){
                button.setEnabled(false);
                button.setBackground(new Color(80, 80, 80));
                button.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2));
                button.setForeground(Color.DARK_GRAY);
            }

            public void mouseReleased(MouseEvent e){
                button.setEnabled(true);
                mouseEntered(e);
            }
        });

        return button;
    }
}
