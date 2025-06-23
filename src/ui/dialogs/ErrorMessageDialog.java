package ui.dialogs;

import ui.legos.CustomButton;
import ui.legos.CustomDialog;
import ui.legos.CustomPanel;
import ui.style.GUIStyle;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ErrorMessageDialog<T> extends CustomDialog<T> {

    public ErrorMessageDialog(Frame parent, String title, String message) {
        super(parent, title, message);
        setSize(getWidth(), 150);
    }

    @Override
    public CustomPanel getOptionPanel() {
        CustomPanel container = new CustomPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(GUIStyle.getGrayColor());
        container.setBorder(new EmptyBorder(0, 15, 10, 15));

        CustomButton okButton = new CustomButton("Ok", 20);
        container.add(okButton, BorderLayout.CENTER);

        okButton.addActionListener(e -> dispose());

        return container;
    }
}
