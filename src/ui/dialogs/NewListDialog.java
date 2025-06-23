package ui.dialogs;

import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import controls.Controller;
import ui.style.GUIStyle;
import ui.ListEditor;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;

public class NewListDialog<T> extends CustomDialog<T> {

    private final Controller<T> controller;

    public NewListDialog(Frame parent, Controller<T> controller) {
        super(parent, "Neue Liste erstellen", "Vergib einen Listenname:");
        this.controller = controller;
        setVisible(true);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setBackground(GUIStyle.getGrayColor());
        container.setBorder(new EmptyBorder(5,5,5,5));
        container.setLayout(new GridBagLayout());

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
        container.add(nameField, gbc);

        // ---------- Line 1: button container ----------
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = getButtonPanel(nameField);
        container.add(buttonPanel, gbc);

        return container;
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
                Controller.handleError("Bitte geben Sie einen gültigen Namen ein");
            }
        });
        buttonPanel.add(submitButton);
        return buttonPanel;
    }
}
