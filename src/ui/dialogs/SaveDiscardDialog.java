package ui.dialogs;

import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

import ui.Launcher;
import ui.ListEditor;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;

public class SaveDiscardDialog<T> extends CustomDialog<T>{    
    Launcher<T> launcher;

    public SaveDiscardDialog(ListEditor<T> listEditor, Launcher<T> launcher){
        super(listEditor, "Änderungen speichern?", "Es existieren ungespeicherte Änderungen.\n Was soll mit diesen gemacht werden?");
        this.launcher = launcher;
        setSize(720, 120);
        setVisible(true);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setBorder(new EmptyBorder(5,5,5,5));
        container.setLayout(new FlowLayout());
        
        CustomButton save = new CustomButton("Speichern", 14);
        save.addActionListener(event -> save());

        CustomButton discard = new CustomButton("Verwerfen", 14);
        discard.addActionListener(event -> discard());

        CustomButton cancel = new CustomButton("Abbrechen", 14);
        cancel.addActionListener(event -> dispose());

        container.add(save);
        container.add(discard);
        container.add(cancel);

        return container;
    }

    private void save(){
        listEditor.saveList();
        dispose();
        listEditor.setVisible(false);
        launcher.setVisible(true);
    }

    private void discard(){
        dispose();
        listEditor.setVisible(false);
        launcher.setVisible(true);
    }
}
