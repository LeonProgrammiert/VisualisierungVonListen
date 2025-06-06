package ui.legos;

import backend.CustomObject;
import controls.Controller;

import javax.swing.*;

public class AddNode extends JDialog {

    private final JTextField textField = new JTextField(20);
    private final JButton saveButton = new JButton("Speichern");
    private final CustomObject current;

    public AddNode(JFrame parent, CustomObject current, int position) {
        super(parent, "Neue Daten hinzufügen", true);
        this.current = current;

        build(parent, position);
    }

    private void build(JFrame parent, int position) {
        setSize(300, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(textField);
        add(Box.createVerticalStrut(10));
        add(saveButton);

        saveButton.addActionListener(e -> {
            CustomObject newData = new CustomObject(textField.getText());

            switch (position) {
                case 0 -> insertAtStart(newData);
                case 1 -> insertAfterCurrent(newData);
                case 2 -> insertAtEnd(newData);
                default -> insertAfterCurrent(newData);
            }

            Controller.getController().getListEditor().openList(newData);
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void insertAfterCurrent(CustomObject obj) {
        CustomObject next = current.getNext();

        current.setNext(obj);
        obj.setPrevious(current);

        obj.setNext(next);
        if (next != null) {
            next.setPrevious(obj);
        }
    }

    private void insertAtEnd(CustomObject obj) {
        CustomObject last = current;
        while (last.getNext() != null) {
            last = last.getNext();
        }

        last.setNext(obj);
        obj.setPrevious(last);
    }

    private void insertAtStart(CustomObject obj) {
        CustomObject first = current;
        while (first.getPrevious() != null) {
            first = first.getPrevious();
        }

        obj.setNext(first);
        first.setPrevious(obj);
    }
}
