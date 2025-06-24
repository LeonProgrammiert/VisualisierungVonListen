package ui.dialogs;

import ui.legos.CustomDialog;
import ui.legos.CustomButton;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;
import ui.ListEditor;
import ui.Launcher;

import java.awt.*;
import javax.swing.border.EmptyBorder;

public class SaveDiscardDialog<T> extends CustomDialog<T>{

    private final ListEditor<T> listEditor;

    public SaveDiscardDialog(ListEditor<T> listEditor){
        super(listEditor, "Änderungen speichern?", "Es existieren ungespeicherte Änderungen!");
        this.listEditor = listEditor;
        setSize(480, 140);
        setVisible(true);
        setLocationRelativeTo(listEditor);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setBorder(new EmptyBorder(5,5,5,5));
        container.setLayout(new FlowLayout());
        
        CustomButton save = new CustomButton("Änderungen speichern", 14);
        save.addActionListener(event -> save());

        CustomButton discard = new CustomButton("Änderungen verwerfen", 14);
        discard.addActionListener(event -> dispose());

        container.add(save);
        container.add(discard);

        return container;
    }

    private void save(){
        listEditor.saveList();
        dispose();
    }
}
