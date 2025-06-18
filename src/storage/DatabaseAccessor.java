package storage;

import backend.CustomObject;
import controls.Controller;

import java.io.*;

// CSV-Dateien erstellen/lesen, aus Zeilen Liste bauen
public class DatabaseAccessor {

    private final String source = "src/saves/";

    public void addList(String name) {
        // Erstelle eine neue leere CSV-Datei im Projektordner
        File file = new File(source + name + ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(""); //schreibt eine leere datei
            System.out.println("CSV-Datei erstellt: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Liste: " + e.getMessage());
        }
    }
    //liest eine vorhandene CSV-Datei und baut eine verkettete Liste auf
    public CustomObject openList(File file) {
        CustomObject first = null;
        CustomObject previous = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip metadata line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(";");//Liest Zeilen einzeln und jede Zeile wird mit ";" in mehrere Teile getrennt.
                CustomObject current = new CustomObject(elements[1]);  //Erzeugt neues CustomObject mit Wert aus zweiten Spalte.

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


