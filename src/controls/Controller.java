package controls;

import backend.CustomObject;
import storage.DatabaseAccessor;
import ui.Launcher;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    private String currentListName;

    public static void main (String[] args) {

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new Controller();
    }


    private final DatabaseAccessor databaseAccessor;
    private final Launcher launcher;


    public Controller() {
        databaseAccessor = new DatabaseAccessor();
        launcher = new Launcher(this);



    }

    public static void handleError(String errorMessage) {
        System.out.println(errorMessage);
        // Später über JOptionPane ausgeben
    }


    public void addList(String name) {
        this.currentListName = name;
        databaseAccessor.addList(name);
        System.out.println("Neue Liste erstellt: " + name);
    }

    public void openList(File file) {
        ArrayList<CustomObject> data = databaseAccessor.openList(file);

        //ListenEditor.load(data);
    }
}
