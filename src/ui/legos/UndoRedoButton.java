package ui.legos;

import backend.ListEvent;
import backend.ListElement;
import controls.Controller;
import ui.style.GUIStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UndoRedoButton extends JPanel {

    private final JLabel iconLabel;
    private boolean isAvailable;

    private final Color unavailableColor = new Color(70, 70, 70);
    private Color currentColor = unavailableColor;

    public UndoRedoButton(String symbol, String tooltip, ListEvent.events event) {
        setLayout(new GridBagLayout());
        setOpaque(false);

        // Kreis-Panel mit Symbol
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GUIStyle.getGrayButtonColor());
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        circlePanel.setPreferredSize(new Dimension(38, 38));
        circlePanel.setOpaque(false);
        circlePanel.setLayout(new BorderLayout());

        // Symbol im Kreis
        iconLabel = GUIStyle.getStyledLabel(symbol, 34, currentColor);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setToolTipText(tooltip);

        circlePanel.add(iconLabel, BorderLayout.CENTER);
        add(circlePanel);

        iconLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isAvailable) {
                    System.out.println("[LOG] Undo/Redo-Button geklickt: " + event.name());
                    ListElement listCopy = Controller.getController().getListEditor().getCurrentListElement().deepCopy();
                    Controller.getController().pull(new ListEvent(listCopy, event));
                }
            }

            public void mouseEntered(MouseEvent e) {
                // Highlights the button if available
                if (isAvailable) iconLabel.setForeground(GUIStyle.getPinkColor());
            }

            public void mouseExited(MouseEvent e) {
                // Resets the highlight to the current state of availability
                iconLabel.setForeground(currentColor);
            }
        });

    }

    public void setAvailable(boolean isAvailable) {
        // Update availability
        this.isAvailable = isAvailable;

        // Update Color
        currentColor = isAvailable ? GUIStyle.getWhiteColor() : unavailableColor;
        iconLabel.setForeground(currentColor);
    }
}