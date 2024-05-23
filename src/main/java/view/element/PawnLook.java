package view.element;

import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.Pawn;

/**
 * The PawnLook class represents the visual representation (look) of a pawn element in the game.
 * It renders the pawn as a single character on a 1x1 grid.
 */
public class PawnLook extends ElementLook {

    /**
     * Constructs a new PawnLook for the specified pawn element.
     * @param element The pawn element to render.
     */
    public PawnLook(Pawn element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1, (element.getStatus() == Pawn.Status.KING_PAWN) ? 0 : 50);
    }

    /**
     * Renders the visual representation of the pawn based on its status.
     */
    protected void render() {
        Pawn pawn = (Pawn) element;
        Pawn.Status status = pawn.getStatus();

        if(pawn.isUnderKing()) {
            shape[0][0] = ConsoleColor.BLACK + Pawn.Status.KING_PAWN.getBackgroundColor() + " " + ConsoleColor.RESET;
        } else {
            // Set the shape character based on the pawn's status and background color
            shape[0][0] = ConsoleColor.BLACK + status.getBackgroundColor() + " " + ConsoleColor.RESET;
        }
    }
}
