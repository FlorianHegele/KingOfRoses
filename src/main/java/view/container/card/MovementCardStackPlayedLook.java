package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

/**
 * The MovementCardStackPlayedLook class represents the visual representation of a played movement card stack using a grid look.
 * It provides specific configurations for rendering the played movement card stack.
 */
public class MovementCardStackPlayedLook extends GridLook {

    /**
     * Constructs a new MovementCardStackPlayedLook with the specified container element.
     *
     * @param containerElement The container element associated with this played movement card stack look.
     */
    public MovementCardStackPlayedLook(ContainerElement containerElement) {
        super(3, 2, containerElement, 2, 1); // Each cell size is 3x2, with border width 2
        setVerticalAlignment(ALIGN_TOP); // Aligns elements vertically at the top of cells
    }

}
