package storage;

import backend.CustomObject;
import controls.Controller;

import java.io.*;
import java.util.ArrayList;

public class DatabaseAccessor {

    public DatabaseAccessor() {
    }


    public void addList() {
        // Create empty csv-file
        System.out.println();
    }

    public ArrayList<CustomObject> openList(File file) {
        // Read the file and returns it values

        ArrayList<CustomObject> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Skip the first line because it's only metadata
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(";");
                data.add(new CustomObject(elements[0], elements[1], elements[2]));
            }
            reader.close();
        }
        catch (IOException e) {
            Controller.handleError(e.getLocalizedMessage());
        }
        return data;
    }
}


