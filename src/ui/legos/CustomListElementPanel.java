package ui.legos;

import backend.ListElement;
import ui.style.GUIStyle;
import ui.ListViewer;

import javax.swing.*;
import java.awt.*;

public class CustomListElementPanel<T> extends JPanel {

    private final ListViewer<T> listViewer;

    public CustomListElementPanel(ListElement<T> element, ListViewer<T> listViewer) {
        this.listViewer = listViewer;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // 1 Zeile, 3 Spalten
        setBorder(BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1));
        setOpaque(false);

        add(getPanel("←", null));
        add(getPanel(element.getElement(), element));
        add(getPanel("→", null));

    }

    public JPanel getPanel(String text, ListElement<T> element) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(GUIStyle.getButtonColor());

        CustomButton button = new CustomButton(text, 28);
        if (element != null) {
            button.addActionListener(e -> listViewer.backToListEditor(element));
        }

        panel.add(button);
        return panel;
    }
}

