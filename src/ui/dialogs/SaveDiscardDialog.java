package ui.dialogs;

import ui.legos.CustomDialog;
import ui.legos.CustomButton;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;
import ui.ListEditor;

import java.awt.*;
import javax.swing.border.EmptyBorder;

import controls.Controller;

public class SaveDiscardDialog<T> extends CustomDialog<T>{

    private final ListEditor<T> listEditor;
    private final Controller<T> controller;

    public SaveDiscardDialog(ListEditor<T> listEditor, Controller<T> controller){
        super(listEditor, "Änderungen speichern?", "Es existieren ungespeicherte Änderungen!");
        this.listEditor = listEditor;
        this.controller = controller;
        setSize(480, 140);
        setVisible(true);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setBorder(new EmptyBorder(5,5,5,5));
        container.setLayout(new FlowLayout());
        
        CustomButton save = new CustomButton("Änderungen speichern", 14);
        save.addActionListener(event -> {
            dispose();
            listEditor.saveList();
        });

        CustomButton discard = new CustomButton("Änderungen verwerfen", 14);
        discard.addActionListener(event -> {
            dispose();
            controller.backToLauncher();
        });

        container.add(save);
        container.add(discard);

        return container;
    }
}
