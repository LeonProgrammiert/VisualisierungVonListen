package ui.legos;

import backend.ListElement;
import backend.ListEvent;
import controls.Controller;
import ui.style.GUIStyle;
import ui.ListViewer;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomListElementPanel<T> extends JPanel {

    private final Controller<T> controller;
    private final ListViewer<T> listViewer;

    private final ListElement<T> listElement;

    public enum buttonTypes {previous, current, next, none}


    public CustomListElementPanel(ListElement<T> element, ListViewer<T> listViewer, Controller<T> controller) {
        this.controller = controller;
        this.listViewer = listViewer;
        this.listElement = element;

        setLayout(new GridLayout(1, 3)); // 1 Zeile, 3 Spalten
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        add(getPanel("←", buttonTypes.previous));
        add(getPanel(element.getElement(), buttonTypes.current));
        add(getPanel("→", buttonTypes.next));

    }

    public JPanel getPanel(String text, buttonTypes type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(GUIStyle.getGrayButtonColor());
        CustomButton button = new CustomButton(text, 28, new EmptyBorder(2, 5, 2, 5));

        button.addActionListener(e -> actionPerformed(type));


        panel.add(button);
        return panel;
    }

    private void actionPerformed(buttonTypes type) {
        switch (type) {
            case previous, next:
                ListElement<T> newList = switchElements(type);
                listViewer.update(newList);
                break;
            case current:
                listViewer.backToListEditor(listElement);
            default:
                System.out.println("unknown button type " + type);

        }
    }

    private ListElement<T> switchElements(buttonTypes type) {
        ListElement<T> current = listElement;

        if (type == buttonTypes.previous) {
            ListElement<T> prev = current.getPrevious();
            if (prev == null) return current; // Can't switch if no previous

            ListElement<T> before = prev.getPrevious();
            ListElement<T> next = current.getNext();

            // Re-link surrounding nodes
            if (before != null) before.setNext(current);
            current.setPrevious(before);
            current.setNext(prev);

            prev.setPrevious(current);
            prev.setNext(next);

            if (next != null) next.setPrevious(prev);

            // Push event
            controller.push(new ListEvent<>(current, ListEvent.events.switchPrevious));
            // Return new "current"
            return current;
        } else {
            ListElement<T> next = current.getNext();
            if (next == null) return current; // Can't switch if no next

            ListElement<T> after = next.getNext();
            ListElement<T> prev = current.getPrevious();

            // Re-link surrounding nodes
            if (prev != null) prev.setNext(next);
            next.setPrevious(prev);
            next.setNext(current);

            current.setPrevious(next);
            current.setNext(after);

            if (after != null) after.setPrevious(current);

            // Push event
            controller.push(new ListEvent<>(current, ListEvent.events.switchNext));
            // Return new "current"
            return next;
        }
    }
}

