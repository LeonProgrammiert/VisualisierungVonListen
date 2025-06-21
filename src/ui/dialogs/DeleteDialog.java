package ui.dialogs;

import backend.ListElement;
import backend.ListEvent;
import controls.Controller;
import ui.ListEditor;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteDialog<T> extends CustomDialog<T> {

    public DeleteDialog(ListEditor<T> listEditor) {
        super(listEditor, "Element löschen", "Was möchtest du löschen?");
        super.setSize(getWidth(), 130);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel buttonPanel = new CustomPanel();
        buttonPanel.setBorder(new EmptyBorder(5,5,5,5));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

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
        return buttonPanel;
    }

    private void clickedDeleteWholeList() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bist du sicher, dass du die gesamte Liste löschen willst?",
                "Sicher?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            deleteWholeList();
            showEmptyListOptions();
        }
    }

    private void deleteWholeList() {
        // löscht wirklich alles
        ListElement<T> currentListElement = listEditor.getCurrentListElement();

        if (currentListElement != null) {
            ListElement<T> first = currentListElement.getFirst();

            while (first != null) {
                ListElement<T> next = first.getNext();
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
        ListElement<T> current = listEditor.getCurrentListElement();

        // Push deep copy of current to the stack
        Controller.getController().push(new ListEvent<>(current.deepCopy(), ListEvent.events.remove));

        // Define next element to display
        ListElement<T> nextToDisplay = remove(current);

        // Display the next element or show emptyListOptions
        dispose();
        //TODO: Save the new list state in csv-file
        if (nextToDisplay != null) {
            listEditor.openList(nextToDisplay);
        } else {
            showEmptyListOptions();
        }
    }

    private void showEmptyListOptions() {
        String[] options = {"Zurück zum Launcher", "Neues Element hinzufügen"};
        int choice = JOptionPane.showOptionDialog(
                listEditor,
                "Die Liste ist jetzt leer. Was möchtest du tun?",
                "Aktion nach dem Löschen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            listEditor.backToLauncher();
        } else if (choice == 1) {
            Controller.getController().openList(Controller.getController().getCurrentListFile());
        }
    }

    public ListElement<T> remove(ListElement<T> current) {
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