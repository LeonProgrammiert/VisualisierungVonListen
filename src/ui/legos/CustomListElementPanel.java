package ui.legos;

import backend.ListElement;
import backend.ListEvent;
import backend.ListUtilities;
import backend.enumerations.SwapPositions;
import controls.Controller;
import ui.style.GUIStyle;
import ui.ListViewer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CustomListElementPanel<T> extends JPanel {

    private final Controller<T> controller;
    private final ListViewer<T> listViewer;

    private final ListElement<T> listElement;

    public enum buttonTypes {previous, current, next, none}

    private final File swapSound;
    private final File clickSound;

    public CustomListElementPanel(ListElement<T> element, ListViewer<T> listViewer, Controller<T> controller) {
        this.controller = controller;
        this.listViewer = listViewer;
        this.listElement = element;

        swapSound = new File(System.getProperty("user.dir") + "/src/assets/swapSound.wav");
        clickSound = new File(System.getProperty("user.dir") + "/src/assets/clickSound.wav");

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // 1 Zeile, 3 Spalten
        setBorder(BorderFactory.createLineBorder(GUIStyle.getUnhighlightedButtonBorderColor(), 1));
        setOpaque(false);

        add(getPanel("←", buttonTypes.previous));
        add(getPanel(element.getElement(), buttonTypes.current));
        add(getPanel("→", buttonTypes.next));

    }

    public JPanel getPanel(String text, buttonTypes type) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(GUIStyle.getButtonColor());
        CustomButton button = new CustomButton(text, 28);

        button.addActionListener(e -> actionPerformed(type));

        panel.add(button);
        return panel;
    }

    private void actionPerformed(buttonTypes type) {
        ListElement<T> newList;
        switch (type) {
            case previous:
                newList = switchElements(SwapPositions.previous);
                listViewer.update(newList);
                break;
            case next:
                newList = switchElements(SwapPositions.next);
                listViewer.update(newList);
                break;
            case current:
                listViewer.backToListEditor(listElement);
                controller.playSound(clickSound);
                break;
            default:
                System.out.println("unknown button type " + type);

        }
    }

    private ListElement<T> switchElements(SwapPositions position) {
        if (ListUtilities.switchAvailable(listElement, position)) {
            // Push event
            controller.push(new ListEvent<>(listElement, ListEvent.events.switchPrevious));
            // Play sound
            controller.playSound(swapSound);
            // Execute switch
            return ListUtilities.switchElements(listElement, position);
        }
        return listElement;
    }
}

