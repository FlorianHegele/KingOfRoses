package view.container.card;

import boardifier.model.ContainerElement;
import view.container.TinyGridLook;

/**
 * The HeroCardStackLook class represents the visual representation of a hero card stack using a tiny grid look.
 * It provides specific configurations for rendering the hero card stack.
 */
public class HeroCardStackLook extends TinyGridLook {

    /**
     * Constructs a new HeroCardStackLook with the specified container element.
     *
     * @param containerElement The container element associated with this hero card stack look.
     */
    public HeroCardStackLook(ContainerElement containerElement) {
        super(2, 2, containerElement); // Each cell size is 2x2
        setVerticalAlignment(ALIGN_MIDDLE); // Aligns elements vertically in the middle of cells
        setHorizontalAlignment(ALIGN_CENTER); // Aligns elements horizontally in the center of cells
    }

}
