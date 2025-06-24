package ui.dialogs;

import backend.ListElement;
import backend.ListEvent;
import controls.Controller;
import ui.ListEditor;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteDialog<T> extends CustomDialog<T> {

    private final ListEditor<T> listEditor;
    private final Controller<T> controller;

    public DeleteDialog(Frame parent, ListEditor<T> listEditor, Controller<T> controller) {
        super(parent, "Element löschen", "Was möchtest du löschen?");
        super.setSize(getWidth(), 130);
        this.listEditor = listEditor;
        this.controller = controller;
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setBackground(GUIStyle.getGrayColor());

        // Cancel button
        CustomButton cancelButton = new CustomButton("Abbrechen", 14);
        cancelButton.addActionListener(e -> dispose());

        // Current element
        CustomButton deleteCurrent = new CustomButton("Nur aktuelles Element", 14);
        deleteCurrent.addActionListener(e -> deleteSingleElement());

        // Whole list
        CustomButton deleteAll = new CustomButton("Komplette Liste", 14);
        deleteAll.addActionListener(e -> clickedDeleteWholeList());

        // Add components
        container.add(cancelButton);
        container.add(deleteCurrent);
        container.add(deleteAll);

        return container;
    }

    private void clickedDeleteWholeList() {
        int confirm = JOptionPane.showConfirmDialog(
                listEditor,
                "Willst du wirklich die gesamte Liste dauerhaft löschen?",
                "Sicherheitsabfrage",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        dispose();

        if (confirm == JOptionPane.YES_OPTION) {
            // Confirmed -> delete
            Controller.getController().deleteList();
            listEditor.backToLauncher();
        }
    }

    private void deleteSingleElement() {
        // Get current Element
        ListElement<T> current = listEditor.getCurrentListElement();

        // Push deep copy of current to the stack
        controller.push(new ListEvent<>(current.deepCopy(), ListEvent.events.remove));

        // Define next element to display and remove the current element
        ListElement<T> nextToDisplay = remove(current);

        // Display the next element or show emptyListOptions
        dispose();
        if (nextToDisplay != null) {
            listEditor.openList(nextToDisplay);
        } else {
            showEmptyListOptions();
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
            // Deletes the list and opens the launcher
            controller.deleteList();
            controller.backToLauncher(null);
        } else if (choice == 1) {
            // Deletes the current element and asks to enter a new one
            controller.addList(controller.getCurrentListName(), true);
        }
    }
}