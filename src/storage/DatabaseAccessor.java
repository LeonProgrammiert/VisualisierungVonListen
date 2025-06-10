package storage;

import backend.CustomObject;
import controls.Controller;

import java.io.*;

public class DatabaseAccessor {

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

    public CustomObject openList(File file) {
        CustomObject first = null;
        CustomObject previous = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip metadata line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(";");
                CustomObject current = new CustomObject(elements[1]);

                if (previous != null) {
                    previous.setNext(current);
                    current.setPrevious(previous);
                } else {
                    first = current; // The current is the first element
                }
                previous = current;
            }
        } catch (IOException e) {
            Controller.handleError(e.getLocalizedMessage());
        }
        return first;
    }
}


