package view.container.card;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

// TODO: Adapt to the new game


public class MovementCardSpreadLook extends GridLook {

    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(1, 1, containerElement, -1, 1);
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);
    }

    protected void renderBorders() {
        Logger.debug("called", this);
        // start by drawing the border of each cell, which will be change after
        for (int i = 0; i < nbRows; i++) {
            //top-left corner
            shape[i * rowHeight][0] = "\u250F";
            // top-right corner
            shape[i * rowHeight][colWidth] = "\u2513";
            //bottom-left corner
            shape[(i + 1) * rowHeight][0] = "\u2517";
            // bottom-right corner
            shape[(i + 1) * rowHeight][colWidth] = "\u251B";

            for (int k = 1; k < colWidth; k++) {
                shape[i * rowHeight][k] = "\u2501";
                shape[(i + 1) * rowHeight][k] = "\u2501";
            }
            // draw left & righ vertical lines
            for (int k = 1; k < rowHeight; k++) {
                shape[i * rowHeight + k][0] = "\u2503";
                shape[i * rowHeight + k][colWidth] = "\u2503";
            }
        }
        // change intersections on first & last vert. border
        for (int i = 1; i < nbRows; i++) {
            shape[i * rowHeight][0] = "\u2523";
            shape[i * rowHeight][colWidth] = "\u252B";
        }
    }
}