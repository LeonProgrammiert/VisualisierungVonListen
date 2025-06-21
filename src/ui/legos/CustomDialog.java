package ui.legos;

import ui.ListEditor;
import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;

public abstract class CustomDialog<T> extends JDialog {

    protected final ListEditor<T> listEditor;

    public CustomDialog(ListEditor<T> listEditor, String title, String message) {
        super(listEditor, title, true);
        this.listEditor = listEditor;

        setValues();
        build(message);
        add(getOptionPanel(), BorderLayout.SOUTH);
}

    private void setValues() {
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(listEditor);
        getContentPane().setBackground(new Color(24,26,28));
    }

    private void build(String message) {
        JLabel label = GUIStyle.getStyledLabel(message, 18);
        add(label, BorderLayout.CENTER);
    }

    public abstract CustomPanel getOptionPanel();
}
