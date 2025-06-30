package ui.legos;

import backend.ListElement;
import ui.style.GUIStyle;
import ui.ListViewer;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;

public class CustomListElementPanel<T> extends JPanel {

    private final ListViewer<T> listViewer;

    public CustomListElementPanel(ListElement<T> element, ListViewer<T> listViewer) {
        this.listViewer = listViewer;

        setLayout(new GridLayout(1, 3)); // 1 Zeile, 3 Spalten
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        add(getPanel("←", null));
        add(getPanel(element.getElement(), element));
        add(getPanel("→", null));

    }

    public JPanel getPanel(String text, ListElement<T> element) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(GUIStyle.getGrayButtonColor());
        CustomButton button = new CustomButton(text, 28, new EmptyBorder(2, 5, 2, 5));

        if (element != null) {
            button.addActionListener(e -> listViewer.backToListEditor(element));
        }

        panel.add(button);
        return panel;
    }
}

