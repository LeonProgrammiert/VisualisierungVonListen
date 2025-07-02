package ui;

import backend.ListElement;
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

    private JPanel contentPanel;
    private CompoundBorder border;

    public ListViewer(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    private void setValues() {
        setTitle("ListViewer");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);

        EmptyBorder emptyBorder = new EmptyBorder(5, 5, 5, 5);
        LineBorder lineBorder = new LineBorder(GUIStyle.getPinkColor());
        border = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
        border = BorderFactory.createCompoundBorder(border, emptyBorder);

        setCloseOperation();
    }

    private void setCloseOperation() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backToListEditor();
            }
        });
    }


    private void build() {
        // Container
        JPanel container = new JPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(GUIStyle.getGrayColor());
        headerPanel.setBorder(border);

        CustomButton backToListEditorButton = new CustomButton("Zurück zum Editor", 18);
        headerPanel.add(backToListEditorButton);
        backToListEditorButton.addActionListener(e -> backToListEditor());

        // Body
        contentPanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        contentPanel.setBackground(GUIStyle.getGrayColor());
        contentPanel.setBorder(border);

        // ScrollPanel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        add(container);
    }


    public void openList(ListElement<T> first) {
        if (isVisible()) {
            return;
        }

        // Start
        JPanel nullPanelStart = new CustomListElementPanel<>(first, this).getPanel("null", null);
        nullPanelStart.setBorder(new LineBorder(Color.BLACK, 2));
        contentPanel.add(nullPanelStart);

        // In between
        ListElement<T> current = first;
        while (current != null) {
            contentPanel.add(new CustomListElementPanel<>(current, this));
            current = current.getNext();
        }

        // End
        JPanel nullPanelEnd = new CustomListElementPanel<>(first, this).getPanel("null", null);
        nullPanelEnd.setBorder(new LineBorder(Color.BLACK, 2));
        contentPanel.add(nullPanelEnd);
    }

    public void backToListEditor() {
        contentPanel.removeAll();
        controller.backToListEditor();
    }

    public void backToListEditor(ListElement<T> current) {
        contentPanel.removeAll();
        controller.backToListEditor(current);
    }
}
