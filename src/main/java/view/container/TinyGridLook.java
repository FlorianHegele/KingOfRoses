package view.container;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

/**
 * The TinyGridLook class represents a simplified grid look with tiny cell borders for visual representation of container elements.
 * It provides a method to render the borders of the grid cells.
 */
public abstract class TinyGridLook extends GridLook {

    /**
     * Constructs a new TinyGridLook with the specified row height, column width, and container element.
     * @param rowHeight The height of each row in the grid.
     * @param colWidth The width of each column in the grid.
     * @param containerElement The container element associated with this grid look.
     */
    protected TinyGridLook(int rowHeight, int colWidth, ContainerElement containerElement) {
        super(rowHeight, colWidth, containerElement, -1, 1);
    }

    /**
     * Renders the borders of the grid cells with tiny border elements.
     */
    @Override
    protected void renderBorders() {
        Logger.debug("called", this);
        // Start by drawing the border of each cell, which will be changed later
        for (int i = 0; i < nbRows; i++) {
            // Top-left corner
            shape[i * rowHeight][0] = "\u250F";
            // Top-right corner
            shape[i * rowHeight][colWidth] = "\u2513";
            // Bottom-left corner
            shape[(i + 1) * rowHeight][0] = "\u2517";
            // Bottom-right corner
            shape[(i + 1) * rowHeight][colWidth] = "\u251B";

            for (int k = 1; k < colWidth; k++) {
                shape[i * rowHeight][k] = "\u2501"; // Horizontal border
                shape[(i + 1) * rowHeight][k] = "\u2501"; // Horizontal border
            }
            // Draw left & right vertical lines
            for (int k = 1; k < rowHeight; k++) {
                shape[i * rowHeight + k][0] = "\u2503"; // Vertical border
                shape[i * rowHeight + k][colWidth] = "\u2503"; // Vertical border
            }
        }
        // Change intersections on first & last vertical border
        for (int i = 1; i < nbRows; i++) {
            shape[i * rowHeight][0] = "\u2523"; // Intersecting border
            shape[i * rowHeight][colWidth] = "\u252B"; // Intersecting border
        }
    }
}
