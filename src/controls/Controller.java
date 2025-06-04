package controls;

import backend.CustomObject;
import storage.DatabaseAccessor;
import ui.Launcher;
import ui.ListenEditor;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    public static void main (String[] args) {
        new Controller();
    }


    private final DatabaseAccessor databaseAccessor;
    private final Launcher launcher;


    public Controller() {
        databaseAccessor = new DatabaseAccessor();
        launcher = new Launcher(this);



    }

    public static void handleError(String localizedMessage) {
        System.out.println(localizedMessage);

    }


    public void addList() {
        databaseAccessor.addList();
    }

    public void openList(File file) {
        ArrayList<CustomObject> data = databaseAccessor.openList(file);

        //ListenEditor.load(data);
    }

}
