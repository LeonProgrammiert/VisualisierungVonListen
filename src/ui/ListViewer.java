package ui;

import backend.ListElement;
import backend.ListUtilities;
import controls.Controller;
import ui.legos.CustomButton;
import ui.legos.CustomListElementPanel;
import ui.legos.WrapLayout;
import ui.style.GUIStyle;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ListViewer<T> extends JFrame {

    private final Controller<T> controller;

    private ListElement<T> currentList;

    private JPanel headerPanel;
    private JPanel contentPanel;
    private CompoundBorder border;


    public ListViewer(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    public void setTheme() {
        EmptyBorder emptyBorder = new EmptyBorder(5, 5, 5, 5);
        LineBorder lineBorder = new LineBorder(GUIStyle.getHighlightedColor());
        border = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
        border = BorderFactory.createCompoundBorder(border, emptyBorder);

        if (contentPanel != null) {
            contentPanel.setBorder(border);
        }
        if (headerPanel != null) {
            headerPanel.setBorder(border);
        }
    }

    private void setValues() {
        setTitle("ListEditor");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);
        setIconImage(GUIStyle.getWindowIcon());

        setTheme();

        setCloseOperation();
    }

    private void setCloseOperation() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backToListEditor(currentList);
            }
        });
    }


    private void build() {
        // Container
        JPanel container = new JPanel();
        container.setBackground(GUIStyle.getBackgroundColor());
        container.setLayout(new BorderLayout());

        // Header
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(GUIStyle.getBackgroundColor());
        headerPanel.setBorder(border);

        CustomButton backToListEditorButton = new CustomButton("Zurück zum Editor", 18);
        headerPanel.add(backToListEditorButton);
        backToListEditorButton.addActionListener(e -> backToListEditor(currentList));

        // Body
        contentPanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        contentPanel.setBackground(GUIStyle.getBackgroundColor());
        contentPanel.setBorder(border);

        // ScrollPanel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        add(container);
    }


    public void openList(ListElement<T> first) {
        // Set current list
        this.currentList = ListUtilities.deepCopy(first);

        // Nur echte Listenelemente anzeigen
        ListElement<T> current = first;
        while (current != null) {
            contentPanel.add(new CustomListElementPanel<>(current, this, controller));
            current = current.getNext();
        }
        repaint();
        revalidate();
    }

    public void backToListEditor(ListElement<T> current) {
        contentPanel.removeAll();
        controller.backToListEditor(current);
    }

    public void update(ListElement<T> newList) {
        // Remove all components and update the frame
        contentPanel.removeAll();
        repaint();
        revalidate();

        // Open new list (saves the current state of the list)
        openList(newList.getFirst());
    }
}
