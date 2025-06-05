package storage;

import backend.CustomObject;
import controls.Controller;
import java.io.*;

public class DatabaseAccessor {

    public DatabaseAccessor() {
    }


    public void addList() {
        // Create empty csv-file
        System.out.println();
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


