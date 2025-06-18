package ui.legos;

import javax.swing.*;
import java.awt.*;

public class UndoRedoButton extends JPanel {

    public UndoRedoButton(String symbol, String tooltip) {
        setLayout(new GridBagLayout());
        setBackground(new Color(24, 26, 28));
        setPreferredSize(new Dimension(80, 80));

        JLabel iconLabel = new JLabel(symbol, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        iconLabel.setForeground(Color.GRAY);
        iconLabel.setToolTipText(tooltip);

        add(iconLabel);
        setOpaque(true);
    }
}