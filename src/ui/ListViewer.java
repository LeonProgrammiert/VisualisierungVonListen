package ui;

import backend.ListElement;
import controls.Controller;
import ui.legos.CustomListElementPanel;
import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;

public class ListViewer<T> extends JFrame {

    private final Controller<T> controller;

    private JPanel container;

    public ListViewer(Controller<T> controller) {
        this.controller = controller;
        setValues();
        build();
    }

    private void setValues() {
        setTitle("ListViewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(GUIStyle.getFrameSize());
        setLocationRelativeTo(null);
    }

    private void build() {
        container = new JPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(container);
    }


    public void openList(ListElement<T> first) {
        ListElement<T> current = first;
        while (current != null) {

            String data = current.getElement();
            container.add(new CustomListElementPanel(data));

            current = current.getNext();
        }
    }
}
