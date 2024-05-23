package view.container;

import boardifier.model.ContainerElement;

/**
 * The PawnPotLook class represents the visual representation of a pawn pot using a tiny grid look.
 * It provides specific configurations for rendering the pawn pot.
 */
public class PawnPotLook extends TinyGridLook {

    /**
     * Constructs a new PawnPotLook with the specified container element.
     *
     * @param containerElement The container element associated with this pawn pot look.
     */
    public PawnPotLook(ContainerElement containerElement) {
        super(2, 2, containerElement); // Each cell size is 2x2
        setVerticalAlignment(ALIGN_MIDDLE); // Aligns elements vertically in the middle of cells
        setHorizontalAlignment(ALIGN_CENTER); // Aligns elements horizontally in the center of cells
    }

}
