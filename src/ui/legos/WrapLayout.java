package ui.legos;

import java.awt.*;

public class WrapLayout extends FlowLayout {

    // alignments: LEFT, CENTER, RIGHT
    public WrapLayout(int align) {
        super(align);
    }

    // Returns the preferred size of the layout
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true); // true = use getPreferredSize()
    }

    // Returns the minimum size of the layout
    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false); // false = use getMinimumSize()

        // Slight correction on the width
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    // Core method: calculates the layout size with or without preferred sizes
    private Dimension layoutSize(Container target, boolean preferred) {
        // Synchronize on the component tree for thread safety
        synchronized (target.getTreeLock()) {

            // Get the width of the target container
            int targetWidth = target.getWidth();
            // If width is not yet set, fall back to parent’s width
            if (targetWidth == 0 && target.getParent() != null) {
                targetWidth = target.getParent().getWidth();
            }

            // Get the container's insets (padding/border)
            Insets insets = target.getInsets();

            // Max available width for components (subtracting padding and gaps)
            int maxWidth = targetWidth - (insets.left + insets.right + getHgap() * 2);

            // Final dimension to return
            Dimension dim = new Dimension(0, 0);

            // Track the current row width and height
            int rowWidth = 0;
            int rowHeight = 0;

            int num_members = target.getComponentCount();
            for (int i = 0; i < num_members; i++) {
                Component m = target.getComponent(i);

                if (m.isVisible()) {
                    // Get component size: preferred or minimum
                    Dimension componentSize = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    // Check if the component fits in the current row
                    if (rowWidth + componentSize.width > maxWidth) {
                        // Start a new row: update total dimensions
                        dim.width = Math.max(dim.width, rowWidth);
                        dim.height += rowHeight;
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    // Add component to the current row
                    rowWidth += componentSize.width + getHgap();
                    rowHeight = Math.max(rowHeight, componentSize.height); // take tallest in the row
                }
            }

            // Add the final row to the total dimensions
            dim.width = Math.max(dim.width, rowWidth);
            dim.height += rowHeight + 65;

            // Add insets and gaps to the total size
            dim.width += insets.left + insets.right + getHgap() * 2;
            dim.height += insets.top + insets.bottom + getVgap() * 2;

            return dim;
        }
    }
}
