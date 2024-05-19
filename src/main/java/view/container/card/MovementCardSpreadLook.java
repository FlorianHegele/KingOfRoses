package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

/**
 * The MovementCardSpreadLook class represents the visual representation of a spread of movement cards using a grid look.
 * It provides specific configurations for rendering the spread of movement cards.
 */
public class MovementCardSpreadLook extends GridLook {

    /**
     * Constructs a new MovementCardSpreadLook with the specified container element.
     * @param containerElement The container element associated with this movement card spread look.
     */
    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(3, 4, containerElement, 2, 1); // Each cell size is 3x4
        setVerticalAlignment(ALIGN_TOP); // Aligns elements vertically at the top of cells
        setHorizontalAlignment(ALIGN_CENTER); // Aligns elements horizontally at the center of cells
    }

}
