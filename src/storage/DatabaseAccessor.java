package storage;

import backend.CustomObject;
import controls.Controller;

import java.io.*;
import java.util.ArrayList;

public class DatabaseAccessor {

    public void addList(String name) {
        // Create empty csv-file
        // Erstelle eine neue leere CSV-Datei im Projektordner
        File file = new File(name + ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("");
            System.out.println("CSV-Datei erstellt: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Liste: " + e.getMessage());
        }
    }

    public CustomObject openList(File file) {
        CustomObject head = null;
        CustomObject previous = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip metadata line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(";");
                CustomObject current = new CustomObject(elements[1]);

                if (previous != null) {
                    previous.setNext(current);
                    current.setPrevious(previous);
                } else {
                    head = current; // First element becomes the head
                }
                previous = current;
            }
        } catch (IOException e) {
            Controller.handleError(e.getLocalizedMessage());
        }
        return head;
    }
}


