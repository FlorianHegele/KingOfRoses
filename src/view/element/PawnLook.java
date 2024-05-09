package view.element;

import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.Pawn;

public class PawnLook extends ElementLook {

    // FIXME : BUG VISUEL, PARFOIS LE PION DU ROI N'EST PAS AFFICHÉ EST AFFICHÉ EN DESSOUS DE CERTAIN PION
    public PawnLook(Pawn element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1, (element.getStatus() == Pawn.Status.KING_PAWN) ? 0 : 1);
    }

    protected void render() {
        Pawn pawn = (Pawn)element;
        Pawn.Status status = pawn.getStatus();

        shape[0][0] = ConsoleColor.BLACK + status.getBackgroundColor() + "P" + ConsoleColor.RESET;
    }

}
