package view.container.card;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import boardifier.view.GridLook;

/**
 * The MovementCardSpreadLook class represents the visual representation of a spread of movement cards using a grid look.
 * It provides specific configurations for rendering the spread of movement cards.
 */
public class MovementCardSpreadLook extends ClassicBoardLook {

    /**
     * Constructs a new MovementCardSpreadLook with the specified container element.
     *
     * @param containerElement The container element associated with this movement card spread look.
     */
    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(3, 4, containerElement, 2, 1, true); // Each cell size is 3x4
        setVerticalAlignment(ALIGN_TOP); // Aligns elements vertically at the top of cells
        setHorizontalAlignment(ALIGN_CENTER); // Aligns elements horizontally at the center of cells
    }

    @Override
    protected void renderCoords() {
        if (!showCoords) return;
        Logger.trace("update coords", this);

        // Ne pas afficher les lettres pour les colonnes, seulement les chiffres
        for (int j = 0; j < nbCols; j++) {
            int n = j + 1;
            int k = innersLeft - 1;
            while (n > 0) {
                shape[0][innersLeft + (int) ((j + 0.5) * colWidth) - k] = String.valueOf(n % 10);
                n = n / 10;
                k--;
            }
        }
    }


}
