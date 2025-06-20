package storage;

import backend.ListElement;
import controls.Controller;

import javax.swing.*;
import java.io.*;

public class DatabaseAccessor<T> {

    private final String source = "src/saves/";

    public void saveList(ListElement first) {

    }

    public boolean addList(String name) {
        // Create empty csv-file
        // Erstelle eine neue leere CSV-Datei im Projektordner

        File file = new File(source + name + ".csv");
        if (file.exists()) {
            int ans = JOptionPane.showConfirmDialog(null, "Es gibt bereits eine Datei mit diesem Namen.\r\nDatei überschreiben?", "Datei existiert bereits", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (ans == JOptionPane.NO_OPTION || ans == JOptionPane.CLOSED_OPTION) {
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("data");
            System.out.println("CSV-Datei erstellt: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Liste: " + e.getMessage());
            return false;
        }
    }

    public ListElement<T> openList(File file) {
        ListElement<T> firstElement = null;
        ListElement<T> previous = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip metadata line

            String line;
            while ((line = reader.readLine()) != null) {
                ListElement<T> current = new ListElement(line);

                if (previous != null) {
                    previous.setNext(current);
                } else {
                    firstElement = current; // Sets the first element of the list
                }
                current.setPrevious(previous);
                previous = current;
            }
        } catch (IOException e) {
            Controller.handleError(e.getLocalizedMessage());
        }
        return firstElement;
    }
}


