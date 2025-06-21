package ui.dialogs;

import controls.Controller;
import ui.ListEditor;
import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewListDialog<T> extends CustomDialog<T> {

    private final Controller<T> controller;

    public NewListDialog(ListEditor<T> listEditor, Controller<T> controller) {
        super(listEditor, "Neue Liste erstellen", "Vergib einen Listenname:");
        this.controller = controller;
        setVisible(true);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel panel = new CustomPanel();
        panel.setBackground(new Color(24,26,28));
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ---------- Line 0: text field ----------
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10); // Abstände
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(18);
        nameField.setPreferredSize(new Dimension(getWidth(), 25));
        nameField.setFont(GUIStyle.getFont(18));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setToolTipText("Listenname");
        panel.add(nameField, gbc);

        // ---------- Line 1: button panel ----------
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = getButtonPanel(nameField);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel getButtonPanel(JTextField nameField) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 20px Abstand zwischen Buttons
        buttonPanel.setOpaque(false);

        CustomButton cancelButton = new CustomButton("Abbrechen", 18);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        CustomButton submitButton = new CustomButton("Bestätigen", 18);
        submitButton.addActionListener(e -> {
            if (!nameField.getText().isEmpty()) {
                dispose();
                controller.addList(nameField.getText());
            } else {
                Controller.handleError("Bitte geben Sie einen Namen ein");
            }
        });
        buttonPanel.add(submitButton);
        return buttonPanel;
    }
}
