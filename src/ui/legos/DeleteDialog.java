package ui.legos;

import backend.ListElement;
import backend.ListEvent;
import controls.Controller;
import ui.ListEditor;

import javax.swing.*;
import java.awt.*;

public class DeleteDialog extends JDialog {

    private ListEditor listEditor;

    public DeleteDialog(ListEditor editor) {
        super(editor, "Element löschen", true);

        this.listEditor = editor;

        setLayout(new BorderLayout());
        setSize(400, 150);
        setLocationRelativeTo(editor);
        getContentPane().setBackground(new Color(24, 26, 28));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel message = new JLabel("Was möchtest du löschen?", SwingConstants.CENTER);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(24, 26, 28));

        CustomButton cancelButton = new CustomButton("Abbrechen", 14);
        cancelButton.addActionListener(e -> dispose());

        CustomButton deleteCurrent = new CustomButton("Nur aktuelles Element", 14);
        deleteCurrent.addActionListener(e -> deleteSingleElement());

        CustomButton deleteAll = new CustomButton("Komplette Liste", 14);
        deleteAll.addActionListener(e -> clickedDeleteWholeList());

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteCurrent);
        buttonPanel.add(deleteAll);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void clickedDeleteWholeList() {
        int confirm = JOptionPane.showConfirmDialog(
                DeleteDialog.this,
                "Bist du sicher, dass du die gesamte Liste löschen willst?",
                "Sicher?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            deleteWholeList();
            showEmptyListOptions(listEditor);
        }
    }

    private void deleteWholeList() {
        // löscht wirklich alles
        ListElement currentListElement = listEditor.getCurrentListElement();

        if (currentListElement != null) {
            ListElement<String> first = currentListElement.getFirst();

            while (first != null) {
                ListElement<String> next = first.getNext();
                first.setPrevious(null);
                first.setNext(null);
                first = next;
            }

            currentListElement = null;
            //TODO: Save the new list state in csv-file

        }
    }

    private void deleteSingleElement() {
        // Get current Element
        ListElement current = listEditor.getCurrentListElement();

        // Push deep copy of current to the stack
        Controller.getController().push(new ListEvent(current.deepCopy(), ListEvent.events.remove));

        // Define next element to display
        ListElement nextToDisplay = remove(current);

        // Display the next element or show emptyListOptions
        dispose();
        //TODO: Save the new list state in csv-file
        if (nextToDisplay != null) {
            listEditor.openList(nextToDisplay);
        } else {
            showEmptyListOptions(listEditor);
        }
    }

    private void showEmptyListOptions(ListEditor editor) {
        String[] options = {"Zurück zum Launcher", "Neues Element hinzufügen"};
        int choice = JOptionPane.showOptionDialog(
                editor,
                "Die Liste ist jetzt leer. Was möchtest du tun?",
                "Aktion nach dem Löschen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            editor.backToLauncher();
        } else if (choice == 1) {
            Controller.getController().openList(Controller.getController().getCurrentListFile());
        }
    }

    public <T> ListElement<T> remove(ListElement<T> current) {
        // Get the previous and next
        ListElement<T> previous = current.getPrevious();
        ListElement<T> next = current.getNext();

        // Update the list
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
        current.setPrevious(null);
        current.setNext(null);

        // Get the next element to display
        if (previous != null) {
            return previous;
        } else return next;
    }
}