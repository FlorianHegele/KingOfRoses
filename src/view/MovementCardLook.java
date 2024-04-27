package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.MovementCard;
import model.Pawn;

public class MovementCardLook extends ElementLook {

    public MovementCardLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1);
    }

    protected void render() {
        MovementCard card = (MovementCard)element;

        shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getDirection() + ConsoleColor.RESET;
    }

}
