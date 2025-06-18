package controls;

import backend.ListElement;
import storage.DatabaseAccessor;
import ui.ListEditor;
import ui.Launcher;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

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

    private static Controller instance;

    public Controller() {
        databaseAccessor = new DatabaseAccessor(); //Zugriff auf Speicher
        instance = this;

        launcher = new Launcher(this);
        launcher.setVisible(true);

        listEditor = new ListEditor(this);
        listEditor.setVisible(false);
    }

    public static Controller getController() {
        return instance;
    }
    public ListEditor getListEditor() {
        return listEditor;
    }

    public static void handleError(String errorMessage) {
        System.out.println(errorMessage);
        // Später über JOptionPane ausgeben
    }

    public void addList(String name) {
        this.currentListName = name;
        databaseAccessor.addList(name);
        System.out.println("Neue Liste erstellt: " + name);
        String path = System.getProperty("user.dir") + "/src/saves/" + name + ".csv";
        openList(new File(path));
    }

    public void openList(File file) {
        // Anker ist das erste Element der Liste und kommt als Rückgabewert vom DatabaseAccessor
        ListElement anker = databaseAccessor.openList(file); // Daten aus CSV laden

        launcher.setVisible(false);
        listEditor.setVisible(true);
        listEditor.openList(anker);
    }

    public void backToLauncher(ListElement anker) { //anker für später zb. export oder speicherfunktionen
        launcher.setVisible(true);
        listEditor.setVisible(false);
    }

    public void playSound(File soundFile) {
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

                // Wait for the clip to finish playing
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (UnsupportedAudioFileException | IOException | InterruptedException |
                     LineUnavailableException e) {
                handleError(e.getMessage());
            }
        }).start();
    }
}
