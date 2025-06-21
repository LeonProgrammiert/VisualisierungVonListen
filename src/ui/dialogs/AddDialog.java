package ui.dialogs;

import backend.enumerations.AddElementPositions;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import backend.ListElement;
import controls.Controller;
import ui.style.GUIStyle;
import backend.ListEvent;
import ui.ListEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddDialog<T> extends CustomDialog<T> {

    private final ListElement<T> current;
    private AddElementPositions position;

    private JTextField textField;
    private JComboBox<String> positionSelector;
    private String[] positionOptions;
    private boolean positionSelectorEditable = true;

    public AddDialog(ListEditor<T> listEditor, ListElement<T> current, AddElementPositions position) {
        // Call of the other constructor
        this(listEditor, current);

        this.position = position;

        // Change the JComboBox options
        this.positionOptions = new String[]{"Als erstes Element", "An den Start", "Als Nächstes", "An das Ende"};
        this.positionSelectorEditable = false;
    }

    public AddDialog(ListEditor<T> listEditor, ListElement<T> current) {
        super(listEditor, "Neue Daten hinzufügen", "Bitte geben Sie neuen Daten ein");

        this.current = current;

        initializePositionSelector();
        setVisible(true);
    }

    private void initializePositionSelector() {
        if (positionOptions == null) this.positionOptions = new String[]{"An den Start", "Als Nächstes", "An das Ende"};

        positionSelector.setModel(new DefaultComboBoxModel<>(positionOptions));
        positionSelector.setEnabled(positionSelectorEditable);
        // If it's the first element, set change the selected position
        positionSelector.setSelectedIndex(positionSelectorEditable ? 1 : 0);
        positionSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Centers the text
        positionSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER); // Zentriert den Text
                return label;
            }
        });
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel optionPanel = new CustomPanel();
        optionPanel.setBorder(new EmptyBorder(5,5,5,5));
        optionPanel.setBackground(super.getContentPane().getBackground());
        optionPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- Line 0: Label and text field ----------
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 0.5;
        JLabel datenLabel = GUIStyle.getStyledLabel("Daten eingeben", 15);
        optionPanel.add(datenLabel, gbc);

        gbc.gridx = 1;
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        optionPanel.add(textField, gbc);

        // ---------- Line 1: Label and ComboBox ----------
        gbc.gridy = 1;

        gbc.gridx = 0;
        JLabel positionLabel = GUIStyle.getStyledLabel("Position wählen", 15);
        optionPanel.add(positionLabel, gbc);

        gbc.gridx = 1;
        positionSelector = new JComboBox<>();
        optionPanel.add(positionSelector, gbc);

        // ---------- Line 2: Cancel- and Save-Button ----------
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        CustomButton cancelButton = new CustomButton("Abbrechen", 14);
        cancelButton.addActionListener(e -> dispose());
        optionPanel.add(cancelButton, gbc);

        gbc.gridx = 1;
        CustomButton saveButton = new CustomButton("Speichern", 14);
        saveButton.addActionListener(e -> clicked());
        optionPanel.add(saveButton, gbc);

        return optionPanel;
    }


    private void clicked() {
        // Testing for empty text field
        if (textField.getText().isEmpty()) {
            Controller.handleError("Keine Daten eingegeben");
            return;
        }

        // Save previous state
        if (current != null) {
            Controller.getController().push(new ListEvent<>(current.deepCopy(), ListEvent.events.add));
        }

        // Inserts new data
        this.position = getSelectedPosition();
        ListElement<T> newData = new ListElement(textField.getText());
        insertData(position, newData);

        // Displays new data
        Controller.getController().getListEditor().openList(newData);
        dispose();
    }

    private AddElementPositions getSelectedPosition() {
        String position = (String) positionSelector.getSelectedItem();
        return switch (position) {
            case "An den Start" -> AddElementPositions.atStart;
            case "An das Ende" -> AddElementPositions.atEnd;
            case "Als erstes Element" -> AddElementPositions.firstElement;
            case null, default -> AddElementPositions.asNext;
        };
    }

    private void insertData(AddElementPositions position, ListElement<T> newData) {
        switch (position) {
            case atStart -> insertAtStart(newData);
            case atEnd -> insertAtEnd(newData);
            case asNext -> insertAfterCurrent(newData);
            case firstElement -> insertAsFirstElementOfList(newData);
        }
    }

    private void insertAsFirstElementOfList(ListElement<T> obj) {
        obj.setPrevious(null);
        obj.setNext(null);
    }

    private void insertAfterCurrent(ListElement<T> obj) {
        ListElement<T> next = current.getNext();

        current.setNext(obj);
        obj.setPrevious(current);

        obj.setNext(next);
        if (next != null) next.setPrevious(obj);
    }

    private void insertAtEnd(ListElement<T> newLast) {
        ListElement<T> previousLast = current.getTail();

        previousLast.setNext(newLast);
        newLast.setPrevious(previousLast);
    }

    private void insertAtStart(ListElement<T> newFirst) {
        ListElement<T> previousFirst = current.getFirst();

        newFirst.setNext(previousFirst);
        previousFirst.setPrevious(newFirst);
    }
}
