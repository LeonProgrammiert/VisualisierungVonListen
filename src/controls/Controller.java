package controls;

import storage.DatabaseAccessor;
import ui.Launcher;
import backend.CustomObject;
import ui.ListEditor;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Controller {

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


    public void addList() {
        databaseAccessor.addList();
    }

    public void openList(File file) {
        CustomObject anker = databaseAccessor.openList(file);

        // Test
        while (anker != null) {
            System.out.println(anker.getData());
            anker = anker.getNext();
        }



        //ListenEditor.load(data);
    }

}
