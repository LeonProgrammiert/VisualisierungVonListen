package controls;

import backend.ListElement;
import backend.ListEvent;
import backend.StackManager;
import storage.DatabaseAccessor;
import ui.ListEditor;
import ui.Launcher;
import ui.ListViewer;
import ui.dialogs.CustomOptionPane;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


//  verbindet UI, Daten und Logik
public class Controller<T> {

    // Current list data
    public static boolean unsavedChanges;
    private String currentListName;
    private File currentListFile;

    // Instances
    private final DatabaseAccessor<T> databaseAccessor;
    private final StackManager<T> stackManager;
    private final ListViewer<T> listViewer;
    private final ListEditor<T> listEditor;
    private final Launcher<T> launcher;

    public static void main(String[] args) {

        // sorgt dafür, dass das Aussehen des Programms dem Betriebssystem angepasst wird
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new Controller<>(); // startet Konstruktor dieser Klasse → damit wird gesamte GUI aufgebaut
    }

    private static Controller instance;

    public Controller() {
        // Initialize
        instance = this;
        unsavedChanges = false;

        // Initialize instances
        stackManager = new StackManager<>(this);
        listEditor = new ListEditor<>(this);
        listViewer = new ListViewer<>(this);
        launcher = new Launcher<>(this);
        databaseAccessor = new DatabaseAccessor<>();

        // Set visibilities
        listEditor.setVisible(false);
        launcher.setVisible(true);
    }

    public static Controller getController() {
        return instance;
    }

    public ListEditor<T> getListEditor() {
        return listEditor;
    }

    public static void displayMessage(String message, String title) {
        //new ErrorMessageDialog<>(null, title, message).setVisible(true);
        CustomOptionPane.showMessageDialog(null, title, message);
    }

    public void addList(String name, boolean overwrite) {
        // Creates a new list
        this.currentListName = name;

        boolean check = databaseAccessor.addList(name, overwrite);

        initializeStacks();
        if (check) {
            String path = System.getProperty("user.dir") + "/src/saves/" + currentListName + ".csv";
            openList(new File(path));
        }
    }

    public void initializeStacks() {
        // Initialize the stacks every time a new list is opened
        stackManager.initialize();
        listEditor.setUndoRedoButtonAvailability(false, false);
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

    public void backToLauncher() {
        // Set visibilities
        launcher.setVisible(true);
        launcher.toFront();
        listEditor.setVisible(false);

        if (listViewer.isVisible()) {
            listViewer.setVisible(false);
        }
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
                displayMessage(e.getMessage(), "Fehlermeldung");
            }
        }).start();
    }

    public void push(ListEvent<T> event) {
        stackManager.push(event);
    }

    public void pull(ListEvent<T> event) {
        // Pulls the previous state of the list and updates the stacks
        ListElement<T> oldState = stackManager.pull(event);

        // Prüft, ob ein Zustand vorhanden ist
        if (oldState == null) {
            displayMessage("Es gibt keinen vorherigen Zustand", "Fehlermeldung");
            return;
            //TODO update unsavedChanges
        }

        // Displays the previous state in the editor
        listEditor.openList(oldState);

    }

    public void saveList(ListElement<T> firstElement) {
        databaseAccessor.saveListToFile(firstElement, currentListFile);
        unsavedChanges = false;
    }

    public String getCurrentListName() {
        return currentListName;
    }

    public void deleteList() {
        boolean confirmed = databaseAccessor.deleteList(currentListFile);
        // Print LOG
        if (confirmed) {
            System.out.println("[LOG] Datei erfolgreich gelöscht: " + currentListFile.getAbsolutePath());
            displayMessage("Datei erfolgreich gelöscht: " + currentListName, "Löschen bestätigt");
        } else {
            System.out.println("[WARN] Datei konnte nicht gelöscht werden.");
        }
    }

    public void updateSaveAvailability(boolean isUnsaved){
        listEditor.updateSaveAvailability(isUnsaved);
    }

    public void openListView(ListElement<T> current) {
        listEditor.setVisible(false);
        listViewer.openList(current.getFirst());
        listViewer.setVisible(true);
    }

    public void backToListEditor() {
        listViewer.setVisible(false);
        listEditor.setVisible(true);
        listEditor.toFront();
    }

    public void backToListEditor(ListElement<T> current) {
        backToListEditor();
        listEditor.openList(current);
    }
}
