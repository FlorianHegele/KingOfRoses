package view.element;

import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.Pawn;

public class PawnLook extends ElementLook {

    public PawnLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1);
    }

    protected void render() {
        Pawn pawn = (Pawn)element;
        Pawn.Status status = pawn.getStatus();

        shape[0][0] = ConsoleColor.BLACK + status.getBackgroundColor() + "P" + ConsoleColor.RESET;
    }

}
