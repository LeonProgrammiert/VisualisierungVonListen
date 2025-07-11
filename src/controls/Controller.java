package controls;

import storage.FileAccessor;
import backend.StackManager;
import backend.ListElement;
import backend.ListEvent;
import ui.style.GUIStyle;
import ui.ListEditor;
import ui.ListViewer;
import ui.Launcher;
import ui.legos.*;

import java.awt.event.MouseListener;
import javax.sound.sampled.*;
import java.io.IOException;
import javax.swing.*;
import java.io.File;
import java.awt.*;


//  verbindet UI, Daten und Logik
public class Controller<T> {

    // Current list data
    public static boolean unsavedChanges;
    private String currentListName;
    private File currentListFile;

    // Instances
    private final FileAccessor<T> fileAccessor;
    private final StackManager<T> stackManager;
    private final ListViewer<T> listViewer;
    private final ListEditor<T> listEditor;
    private final Launcher<T> launcher;

    private static Controller instance;

    private static boolean darkMode = true;

    public static void main(String[] args) {

        // Sorgt dafür, dass das Aussehen des Programms dem Betriebssystem angepasst wird
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Set color theme
        GUIStyle.setColorMode(darkMode);

        // Startet Konstruktor dieser Klasse → damit wird gesamte GUI aufgebaut
        instance = new Controller<>();

    }

    public Controller() {
        // Initialize
        unsavedChanges = false;

        GUIStyle.setColorMode(true);

        // Initialize instances
        stackManager = new StackManager<>(this);
        listEditor = new ListEditor<>(this);
        listViewer = new ListViewer<>(this);
        launcher = new Launcher<>(this);
        fileAccessor = new FileAccessor<>();

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

        boolean check = fileAccessor.addList(name, overwrite);

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
        ListElement<T> firstElement = fileAccessor.openList(file);

        // Opens the list in the ListEditor
        launcher.setVisible(false);
        listEditor.openList(firstElement);

        if (!listEditor.isVisible()) {
            System.out.println("[LOG] Fenster war unsichtbar – wird sichtbar gemacht");
            listEditor.setVisible(true);
        }
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
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Thread.sleep() is too imprecise -> leads to delayed or weird audio
            // LineListener is exact because it listens to what the Clip-object is doing directly
            // closes the clip once it is finished -> no memory leak
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            displayMessage(e.getMessage(), "Fehlermeldung");
        }
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
        }

        // Displays the previous state in the editor
        listEditor.openList(oldState);

    }

    public void saveList(ListElement<T> firstElement) {
        fileAccessor.saveListToFile(firstElement, currentListFile);
        unsavedChanges = false;
    }

    public String getCurrentListName() {
        return currentListName;
    }

    public void deleteList() {
        boolean confirmed = fileAccessor.deleteList(currentListFile);
        // Print LOG
        if (confirmed) {
            System.out.println("[LOG] Datei erfolgreich gelöscht: " + currentListFile.getAbsolutePath());
            displayMessage("Datei erfolgreich gelöscht: " + currentListName, "Löschen bestätigt");
        } else {
            System.out.println("[WARN] Datei konnte nicht gelöscht werden.");
        }
    }

    public void openListView(ListElement<T> current) {
        if (!listViewer.isVisible()) {
            listEditor.setVisible(false);
            listViewer.openList(current.getFirst());
            listViewer.setVisible(true);
        }
    }

    public void backToListEditor(ListElement<T> current) {
        listViewer.setVisible(false);
        listEditor.setVisible(true);
        listEditor.toFront();
        listEditor.openList(current);
    }

    public void toggleTheme() {
        darkMode = !darkMode;
        GUIStyle.setColorMode(darkMode);

        updateThemeForWindow(launcher);
        // Has to be updated manually
        launcher.setCentralPanelTheme();

        updateThemeForWindow(listEditor);
        updateThemeForWindow(listViewer);
        listViewer.setTheme();
    }

    private void updateThemeForWindow(JFrame frame) {
        if (frame == null) return;
        updateComponentTreeColors(frame.getContentPane());

        frame.repaint();
        frame.revalidate();
    }

    public static void updateComponentTreeColors(Component comp) {
        if (comp instanceof JPanel panel) {
            panel.setBackground(GUIStyle.getBackgroundColor());
        }
        if (comp instanceof CustomPanel button) {
            button.setBackground(GUIStyle.getButtonColor());
            button.setForeground(GUIStyle.getFontColor());

            // Updates hover-effect
            if (comp instanceof CustomButton) {
                MouseListener[] listeners = comp.getMouseListeners();
                for (MouseListener listener : listeners) {
                    if (listener instanceof CustomMouseListener) {
                        ((CustomMouseListener) listener).setTheme();
                    }
                }
            }
        }
        if (comp instanceof SaveButton) {
            ((SaveButton) comp).setTheme();
        }
        if (comp instanceof JToggleButton toggle) {
            toggle.setBackground(GUIStyle.getButtonColor());
            toggle.setForeground(GUIStyle.getFontColor());
        }
        if (comp instanceof JLabel label) {
            label.setForeground(GUIStyle.getFontColor());
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                updateComponentTreeColors(child);
            }
        }
        comp.repaint();
        comp.revalidate();
    }
}
