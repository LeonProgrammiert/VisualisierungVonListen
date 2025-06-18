package ui.legos;

import backend.ListElement;
import controls.Controller;

import javax.swing.*;
import java.awt.*;

public class AddNode<T> extends JFrame {

    private JTextField textField;
    private final ListElement<T> current;

    public AddNode(ListElement<T> current, int position) {
        this.current = current;
        build(position);
    }

    private void build(int position) {
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

        saveButton.addActionListener(e -> {
            ListElement newData = new ListElement(textField.getText());

            switch (position) {
                case -10 -> insertAsFirst(newData);
                case 0 -> insertAtStart(newData);
                case 2 -> insertAtEnd(newData);
                default -> insertAfterCurrent(newData);
            }

            Controller.getController().getListEditor().openList(newData);
            dispose();
        });

        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }


    private void insertAsFirst(ListElement<T> obj) {
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

    private void insertAtEnd(ListElement<T> obj) {
        ListElement<T> last = current;
        while (last.getNext() != null) {
            last = last.getNext();
        }

        last.setNext(obj);
        obj.setPrevious(last);
    }

    private void insertAtStart(ListElement<T> obj) {
        ListElement<T> first = current;
        while (first.getPrevious() != null) {
            first = first.getPrevious();
        }

        obj.setNext(first);
        first.setPrevious(obj);
    }
}
