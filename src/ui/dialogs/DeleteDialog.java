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
import java.io.File;

public class DeleteDialog<T> extends CustomDialog<T> {

    private final ListEditor<T> listEditor;

    public DeleteDialog(Frame parent, ListEditor<T> listEditor) {
        super(parent, "Element löschen", "Was möchtest du löschen?");
        super.setSize(getWidth(), 130);
        this.listEditor = listEditor;
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

            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nutzer hat abgebrochen
            }

            // Speicher löschen
            ListElement<T> current = listEditor.getCurrentListElement();
            if (current != null) {
                ListElement<T> first = current.getFirst();
                while (first != null) {
                    ListElement<T> next = first.getNext();
                    first.setPrevious(null);
                    first.setNext(null);
                    first = next;
                }
            }

            // UI leeren
            listEditor.clearData();
            listEditor.changeUnsavedStatus(false);

            // Datei löschen
            File fileToDelete = Controller.getController().getCurrentListFile();
            if (fileToDelete != null && fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
                if (deleted) {
                    System.out.println("[LOG] Datei erfolgreich gelöscht: " + fileToDelete.getAbsolutePath());
                } else {
                    System.out.println("[WARN] Datei konnte nicht gelöscht werden.");
                }
            }

            // Controller zurücksetzen
            Controller.getController().setCurrentListElement(null);
            Controller.getController().setSkipSavePrompt(true);

            JOptionPane.showMessageDialog(
                    listEditor,
                    "Die Liste wurde erfolgreich gelöscht.",
                    "Bestätigung",
                    JOptionPane.INFORMATION_MESSAGE
            );
            listEditor.backToLauncher();

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
        if (nextToDisplay != null) {
            listEditor.openList(nextToDisplay);
        } else {
                // Sicherheitsabfrage nur wenn Liste dadurch wirklich leer wäre
                int confirm = JOptionPane.showConfirmDialog(
                        listEditor,
                        "Das war das letzte Element. Willst du wirklich die gesamte Liste dauerhaft löschen?",
                        "Sicherheitsabfrage",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return; //Abbruch
                }

                listEditor.clearData();
                listEditor.changeUnsavedStatus(false);

                // Datei löschen
                File fileToDelete = Controller.getController().getCurrentListFile();
                if (fileToDelete != null && fileToDelete.exists()) {
                    boolean deleted = fileToDelete.delete();
                    if (deleted) {
                        System.out.println("[LOG] Datei erfolgreich gelöscht: " + fileToDelete.getAbsolutePath());
                    } else {
                        System.out.println("[WARN] Datei konnte nicht gelöscht werden.");
                    }
                }

                // Zustand zurücksetzen
                Controller.getController().setCurrentListElement(null);
                Controller.getController().setSkipSavePrompt(true);
                JOptionPane.showMessageDialog(
                        listEditor,
                        "Die Liste wurde erfolgreich gelöscht.",
                        "Bestätigung",
                        JOptionPane.INFORMATION_MESSAGE
                );
                listEditor.backToLauncher();
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