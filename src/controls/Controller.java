package controls;

import backend.ListElement;
import backend.ListEvent;
import backend.StackManager;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;
import storage.DatabaseAccessor;
import ui.Launcher;
import ui.ListEditor;
import ui.dialogs.SaveDiscardDialog;

//  verbindet UI, Daten und Logik
public class Controller <T> {

    private String currentListName;
    private File currentListFile;
    private boolean unsavedChanges;

    private final DatabaseAccessor<T> databaseAccessor;
    private final Launcher<T> launcher;
    private final ListEditor<T> listEditor;
    private final StackManager<T> stackManager;

    public static void main (String[] args) {

        // sorgt dafür, dass das Aussehen des Programms dem Betriebssystem angepasst wird
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new Controller<>(); // startet Konstruktor dieser Klasse → damit wird gesamte GUI aufgebaut
    }

    private static Controller instance;

    public Controller() {
        instance = this;

        unsavedChanges = false;

        databaseAccessor = new DatabaseAccessor<>();
        stackManager = new StackManager<>(this);

        launcher = new Launcher(this);
        launcher.setVisible(true);

        listEditor = new ListEditor<>(this);
        listEditor.setVisible(false);
    }

    public static Controller getController() {
        return instance;
    }

    public ListEditor<T> getListEditor() {
        return listEditor;
    }

    public static void handleError(String errorMessage) {
        System.out.println(errorMessage);
        // Später über JOptionPane ausgeben
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addList(String name) {
        // Creates a new list
        this.currentListName = name;
        boolean check = databaseAccessor.addList(name);
        if (check) {
            String path = System.getProperty("user.dir") + "/src/saves/" + name + ".csv";
            initializeStacks();
            openList(new File(path));
        }
    }

    public void initializeStacks() {
        // Initialize the stacks every time a new list is opened
        stackManager.initialize();
    }

    public void openList(File file) {
        currentListName = file.getName().replaceFirst("[.][^.]+$", "");
        currentListFile = file;

        // Anker ist das erste Element der Liste und kommt als Rückgabewert vom DatabaseAccessor
        ListElement<T> firstElement = databaseAccessor.openList(file);

        // Opens the list in the ListEditor
        launcher.setVisible(false);
        listEditor.openList(firstElement);
    }

    public void backToLauncher(ListElement<T> anker) {
        if(unsavedChanges){
            new SaveDiscardDialog<>(listEditor, launcher);
        } else{
            launcher.setVisible(true);
            listEditor.setVisible(false);
        }
        unsavedChanges = false;
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

    public void push(ListEvent<T> event) {
        stackManager.push(event);
        unsavedChanges = true;
    }

    public void pull(ListEvent<T> event) {
        // Pulls the previous state of the list and updates the stacks
        ListElement<T> oldState = stackManager.pull(event);

        // Prüft, ob ein Zustand vorhanden ist
        if (oldState == null) {
            handleError("Es gibt keinen vorherigen Zustand");
            /*
            JOptionPane.showMessageDialog(
                    listEditor,
                    "Es gibt keinen vorherigen Zustand.",
                    "Aktion nicht möglich",
                    JOptionPane.INFORMATION_MESSAGE
            );
             */
            return;
        }

        // Displays the previous state in the editor
        listEditor.openList(oldState);

    }

    public void saveList(ListElement<T> firstElement){
        if(unsavedChanges){
            databaseAccessor.saveListToFile(firstElement, currentListFile);
            unsavedChanges = false;
        }
    }

    public File getCurrentListFile() {
        return currentListFile;
    }
}
