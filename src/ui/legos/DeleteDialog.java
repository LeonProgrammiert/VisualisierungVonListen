package ui.legos;

import ui.ListEditor;

import javax.swing.*;
import java.awt.*;

public class DeleteDialog extends JDialog {

    public DeleteDialog(ListEditor editor) {
        super(editor, "Element löschen", true);
        setLayout(new BorderLayout());
        setSize(500, 160);
        setLocationRelativeTo(editor);
        getContentPane().setBackground(new Color(24, 26, 28));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel message = new JLabel("Was möchtest du löschen?", SwingConstants.CENTER);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(24, 26, 28));

        CustomButton cancelButton = new CustomButton("Abbrechen", 14);
        cancelButton.addActionListener(e -> dispose());

        CustomButton deleteCurrent = new CustomButton("Nur aktuelles Element", 14);
        deleteCurrent.addActionListener(e -> {
            dispose();
            editor.onDeleteCurrent();

            if (editor.getCurrentListElement() == null) {
                showEmptyListOptions(editor);
            }
        });

        CustomButton deleteAll = new CustomButton("Komplette Liste", 14);
        deleteAll.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    DeleteDialog.this,
                    "Bist du sicher, dass du die gesamte Liste löschen willst?",
                    "Sicher?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                editor.onDeleteAll(); // löscht wirklich alles
                showEmptyListOptions(editor);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteCurrent);
        buttonPanel.add(deleteAll);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showEmptyListOptions(ListEditor editor) {
        String[] options = {"Zurück zum Launcher", "Neues Element hinzufügen"};
        int choice = JOptionPane.showOptionDialog(
                editor,
                "Die Liste ist jetzt leer. Was möchtest du tun?",
                "Aktion nach dem Löschen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            editor.backToLauncher();
        } else if (choice == 1) {
            editor.addNodeAtStart();
        }
    }
}