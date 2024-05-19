package view.container.card;

import boardifier.model.ContainerElement;
import view.container.TinyGridLook;

/**
 * The MovementCardStackLook class represents the visual representation of a movement card stack using a tiny grid look.
 * It provides specific configurations for rendering the movement card stack.
 */
public class MovementCardStackLook extends TinyGridLook {

    /**
     * Constructs a new MovementCardStackLook with the specified container element.
     * @param containerElement The container element associated with this movement card stack look.
     */
    public MovementCardStackLook(ContainerElement containerElement) {
        super(2, 2, containerElement); // Each cell size is 2x2
        setVerticalAlignment(ALIGN_MIDDLE); // Aligns elements vertically at the middle of cells
        setHorizontalAlignment(ALIGN_CENTER); // Aligns elements horizontally at the center of cells
    }

}
