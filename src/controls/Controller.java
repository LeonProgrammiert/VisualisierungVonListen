package controls;

import backend.CustomObject;
import storage.DatabaseAccessor;
import backend.CustomObject;
import ui.ListEditor;
import ui.Launcher;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

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

    private static Controller instance;

    public Controller() {
        instance = this;

        databaseAccessor = new DatabaseAccessor();
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
    }

    public void openList(File file) {
        CustomObject anker = databaseAccessor.openList(file);

        launcher.setVisible(false);
        listEditor.setVisible(true);
        listEditor.openList(anker);
    }

    public void backToLauncher(CustomObject anker) {
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
