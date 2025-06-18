package ui.legos;

import javax.swing.*;
import java.awt.*;

public class UndoRedoButton extends JPanel {

    public UndoRedoButton(String symbol, String tooltip) {
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Kreis-Panel mit Symbol
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 40));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        circlePanel.setPreferredSize(new Dimension(38, 38));
        circlePanel.setOpaque(false);
        circlePanel.setLayout(new BorderLayout());

        // Symbol im Kreis
        JLabel iconLabel = new JLabel(symbol, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 34));
        iconLabel.setForeground(new Color(70, 70, 70));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setToolTipText(tooltip);

        circlePanel.add(iconLabel, BorderLayout.CENTER);
        add(circlePanel);
    }
}