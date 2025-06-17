package storage;

import backend.CustomObject;
import controls.Controller;

import java.io.*;

public class DatabaseAccessor<T> {

    private final String source = "src/saves/";

    public void addList(String name) {
        // Create empty csv-file
        // Erstelle eine neue leere CSV-Datei im Projektordner
        File file = new File(source + name + ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("");
            System.out.println("CSV-Datei erstellt: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Liste: " + e.getMessage());
        }
    }

    public CustomObject<T> openList(File file) {
        CustomObject<T> head = null;
        CustomObject<T> previous = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip metadata line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                CustomObject<T> current = new CustomObject(line);

                if (previous != null) {
                    previous.setNext(current);
                } else {
                    head = current; // First element becomes the head
                }
                current.setPrevious(previous);
                previous = current;
            }
        } catch (IOException e) {
            Controller.handleError(e.getLocalizedMessage());
        }
        return head;
    }
}


