package ui.legos;

import backend.ListEvent;
import backend.ListElement;
import controls.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddElementPopUp<T> extends JFrame {

    public enum positions {firstElement, atStart, atEnd, asNext}

    private JTextField textField;
    private final ListElement<T> current;

    public AddElementPopUp(ListElement<T> current, positions position) {
        this.current = current;
        build(position);
    }

    private void build(positions position) {
        setTitle("Neue Daten hinzufügen");
        setSize(600, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new Label("Neue Daten:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        textField = new JTextField(30); // Optional: set preferred column width
        add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = new JButton("Speichern");
        add(saveButton, gbc);

        // Could add an ActionListener but in future versions
        // We are going to use the custom button
        saveButton.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
                // Save previous state
                Controller.getController().push(new ListEvent<T>(current.deepCopy(), ListEvent.events.add));

                // Inserts new data
                ListElement newData = new ListElement(textField.getText());
                insertData(position, newData);

                // Displays new data
                Controller.getController().getListEditor().openList(newData);
                dispose();
           }
        });

        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void insertData(positions position, ListElement<T> newData) {
        switch (position) {
            case atStart -> insertAtStart(newData);
            case atEnd -> insertAtEnd(newData);
            case asNext -> insertAfterCurrent(newData);
            case firstElement -> insertAsFirstElementOfList(newData);
        }
    }

    private void insertAsFirstElementOfList(ListElement<T> obj) {
        obj.setPrevious(null);
        obj.setNext(null);
    }

    private void insertAfterCurrent(ListElement<T> obj) {
        ListElement<T> next = current.getNext();

        current.setNext(obj);
        obj.setPrevious(current);

        obj.setNext(next);
        if (next != null) {
            next.setPrevious(obj);
        }
    }

    private void insertAtEnd(ListElement<T> newLast) {
        ListElement<T> previouslast = newLast.getTail();

        previouslast.setNext(newLast);
        newLast.setPrevious(previouslast);
    }

    private void insertAtStart(ListElement<T> newFirst) {
        ListElement<T> previousFirst = newFirst.getFirst();

        newFirst.setNext(previousFirst);
        previousFirst.setPrevious(newFirst);
    }
}
