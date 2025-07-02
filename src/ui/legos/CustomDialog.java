package ui.legos;

import ui.ListEditor;
import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;

public abstract class CustomDialog<T> extends JDialog {

    private final Frame parent;

    public CustomDialog(Frame parent, String title, String message) {
        super(parent, title, true);
        this.parent = parent;

        setValues();
        build(message);
        add(getOptionPanel(), BorderLayout.SOUTH);
    }

    private void setValues() {
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(GUIStyle.getBackgroundColor());
    }

    private void build(String message) {
        JLabel label = GUIStyle.getStyledLabel(message, 18);
        add(label, BorderLayout.CENTER);
    }

    public abstract CustomPanel getOptionPanel();
}
