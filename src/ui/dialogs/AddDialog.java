package ui.dialogs;

import backend.ListUtilities;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddDialog<T> extends CustomDialog<T> {

    private final ListEditor<T> listEditor;

    private final ListElement<T> current;
    private AddElementPositions position;

    private JTextField textField;
    private JComboBox<String> positionSelector;
    private String[] positionOptions = new String[]{"An den Start", "Als Nächstes", "An das Ende"};
    private boolean positionSelectorEditable = true;
    private int maxInputLength = 12;

    public AddDialog(Frame parent, ListEditor<T> listEditor, ListElement<T> current, AddElementPositions position) {
        // Call of the other constructor
        this(parent, listEditor, current, false);
        this.position = position;

        // Change the JComboBox options
        this.positionOptions = new String[]{"Als erstes Element", "An den Start", "Als Nächstes", "An das Ende"};
        this.positionSelectorEditable = false;
        initializePositionSelector();
        setVisible(true);
    }

    public AddDialog(Frame parent, ListEditor<T> listEditor, ListElement<T> current, boolean visible) {
        super(parent, "Neue Daten hinzufügen", "Bitte geben Sie neuen Daten ein");

        this.listEditor = listEditor;
        this.current = current;

        initializePositionSelector();
        setVisible(visible);
    }

    private void initializePositionSelector() {
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
        
        // pressing the enter key while in this dialog acts the same as clicking the save button
        KeyAdapter enterKey = new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    clicked();
                }
            }
        };
        
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
        textField.setToolTipText("max 12 Zeichen");
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(enterKey);
        optionPanel.add(textField, gbc);

        // ---------- Line 1: Label and ComboBox ----------
        gbc.gridy = 1;

        gbc.gridx = 0;
        JLabel positionLabel = GUIStyle.getStyledLabel("Position wählen", 15);
        optionPanel.add(positionLabel, gbc);

        gbc.gridx = 1;
        positionSelector = new JComboBox<>();
        positionSelector.addKeyListener(enterKey);
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
            Controller.displayMessage("Keine Daten eingegeben", "Fehlermeldung");
            return;
        }

        // Testing if input is too long
        if (textField.getText().length() > maxInputLength) {
            Controller.displayMessage("Maximal " + maxInputLength + " Zeichen erlaubt!", "Fehlermeldung");
            return;
        }

        // Save previous state
        if (current != null) {
            Controller.getController().push(new ListEvent<>(current, ListEvent.events.add));
        }

        // Inserts new data
        this.position = getSelectedPosition();
        ListElement<T> newData = new ListElement(textField.getText());
        insertData(position, newData);

        Controller.unsavedChanges = true;
        listEditor.updateSaveAvailability(Controller.unsavedChanges);

        // Displays new data
        listEditor.openList(newData);
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
