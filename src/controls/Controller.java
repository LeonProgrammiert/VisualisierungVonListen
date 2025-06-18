package controls;
import backend.CustomObject;
import storage.DatabaseAccessor;
import ui.ListEditor;
import ui.Launcher;

import javax.swing.*;
import java.io.File;

//  verbindet UI, Daten und Logik
public class Controller {

    private String currentListName;
    private final DatabaseAccessor databaseAccessor;
    private final Launcher launcher;
    private final ListEditor listEditor;

    public static void main (String[] args) {

        // sorgt dafür, dass das Aussehen des Programms dem Betriebssystem angepasst wird
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new Controller(); // startet Konstruktor dieser Klasse → damit wird gesamte GUI aufgebaut
    }

    public Controller() {
        databaseAccessor = new DatabaseAccessor(); //Zugriff auf Speicher
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
        CustomObject anker = databaseAccessor.openList(file); // Daten aus CSV laden

        launcher.setVisible(false);
        listEditor.setVisible(true);
        listEditor.openList(anker);
    }

    public void backToLauncher(CustomObject anker) { //anker für später zb. export oder speicherfunktionen
        launcher.setVisible(true);
        listEditor.setVisible(false);
    }
}
