package storage;

import backend.ListElement;
import controls.Controller;

import javax.swing.*;
import java.io.*;

public class FileAccessor<T> {

    private final String source = "src/saves/";

    public boolean deleteList(File listFile){
        boolean deleted = false;

        // Delete file
        if (listFile != null && listFile.exists()) {
            deleted = listFile.delete();
        }

        return deleted;
    }

    public boolean saveListToFile(ListElement<T> first, File listFile){    
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(listFile))){
            ListElement<T> current = first;

            // the first line of the file should be changed depending on 
            // how custom objects are implemented in the future
            fileWriter.write("metadata/attribute names");
            fileWriter.newLine();

            while(current != null){
                fileWriter.write(current.getElement());
                fileWriter.newLine();
                current = current.getNext();
            }
            
            fileWriter.close();

            System.out.println("CSV-Datei gespeichert: " + listFile.getAbsolutePath());
            return true;
        } catch(IOException e){
            System.err.println("Fehler beim Speichern der Liste: " + e.getMessage());
            return false;
        }
    }

    public boolean addList(String name, boolean overwrite) {
        // Create empty csv-file
        // Erstelle eine neue leere CSV-Datei im Projektordner

        File file = new File(source + name + ".csv");
        if (file.exists() && !overwrite) {
            int ans = JOptionPane.showConfirmDialog(null, "Es gibt bereits eine Datei mit diesem Namen.\r\nDatei überschreiben?", "Datei existiert bereits", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (ans == JOptionPane.NO_OPTION || ans == JOptionPane.CLOSED_OPTION) {
                return false;
            }
        }

        return saveListToFile(null, file);
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
            Controller.displayMessage(e.getLocalizedMessage(), "Fehlermeldung");
        }
        return firstElement;
    }
}
