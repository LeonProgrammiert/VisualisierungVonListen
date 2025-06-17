package controls;

import backend.CustomObject;
import storage.DatabaseAccessor;
import ui.ListEditor;
import ui.Launcher;

import javax.swing.*;
import java.io.File;

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
    private final ListEditor listEditor;

    public Controller() {
        databaseAccessor = new DatabaseAccessor();
        launcher = new Launcher(this);
        launcher.setVisible(true);

        listEditor = new ListEditor(this);
        listEditor.setVisible(false);
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
        // Anker ist das erste Element der Liste und kommt als Rückgabewert vom DatabaseAccessor
        CustomObject anker = databaseAccessor.openList(file);

        launcher.setVisible(false);
        listEditor.setVisible(true);
        listEditor.openList(anker);
    }

    public void backToLauncher(CustomObject anker) {
        launcher.setVisible(true);
        listEditor.setVisible(false);
    }
}
