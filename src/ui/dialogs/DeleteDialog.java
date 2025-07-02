package ui.dialogs;

import backend.ListElement;
import backend.ListEvent;
import controls.Controller;
import ui.ListEditor;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomOptionPane;
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
    public CustomPanel getOptionPanel()  {
        CustomPanel container = new CustomPanel();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setBackground(GUIStyle.getBackgroundColor());

        // Cancel button
        CustomButton cancelButton = new CustomButton("Abbrechen", 14);
        cancelButton.addActionListener(e -> dispose());

        // Current element
        CustomButton deleteCurrent = new CustomButton("Nur aktuelles Element", 14);
        deleteCurrent.addActionListener(e -> deleteSingleElement());

        // Whole list
        CustomButton deleteAll = new CustomButton("Komplette Liste", 14);
        deleteAll.addActionListener(e ->    clickedDeleteWholeList());

        // Add components
        container.add(cancelButton);
        container.add(deleteCurrent);
        container.add(deleteAll);

        return container;
    }

    private void clickedDeleteWholeList() {
        int confirm = CustomOptionPane.showConfirmDialog(null,
                "Sicherheitsabfrage",
                "Willst du wirklich die gesamte Liste dauerhaft löschen?");

        dispose();

        if (confirm == JOptionPane.YES_OPTION) {
            // Confirmed -> delete
            listEditor.saveList();
            Controller.getController().deleteList();
            listEditor.backToLauncher();
        }

    }

    private void deleteSingleElement() {
        // Get current Element
        ListElement<T> current = listEditor.getCurrentListElement();

        // Push deep copy of current to the stack
        controller.push(new ListEvent<>(current, ListEvent.events.remove));

        // Define next element to display and remove the current element
        ListElement<T> nextToDisplay = remove(current);

        // Close the dialog
        dispose();

        // Display the next element or show emptyListOptions
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
        CustomDialog<T> dialog = new CustomDialog<>(listEditor, "Aktion nach dem Löschen", "Die Liste ist jetzt leer. Was möchtest du tun?") {

            @Override
            public CustomPanel getOptionPanel() {
                CustomPanel panel = new CustomPanel();
                panel.setBackground(GUIStyle.getBackgroundColor());
                panel.setBorder(new EmptyBorder(0, 5, 5, 5));

                panel.setLayout(new GridLayout(2, 1, 0, 10));

                CustomButton launcherButton = new CustomButton("Zurück zum Launcher", 14);
                launcherButton.addActionListener(e -> {
                    dispose();
                    controller.deleteList();
                    controller.backToLauncher();
                });

                CustomButton addButton = new CustomButton("Neues Element hinzufügen", 14);
                addButton.addActionListener(e -> {
                    dispose();
                    controller.addList(controller.getCurrentListName(), true);
                });

                panel.add(launcherButton);

                panel.add(addButton);
                return panel;
            }
        };

        dialog.setVisible(true);
    }
}