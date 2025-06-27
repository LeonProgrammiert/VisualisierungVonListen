package ui;

import backend.ListElement;
import controls.Controller;
import ui.style.GUIStyle;

import javax.swing.*;

public class ListViewer<T> extends JFrame {

    private final Controller<T> controller;

    public ListViewer(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    private void setValues() {
        setTitle("ListViewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);
    }

    private void build() {

    }


    public void openList(ListElement<T> first) {

    }
}
