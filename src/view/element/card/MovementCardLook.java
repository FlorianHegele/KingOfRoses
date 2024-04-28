package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.MovementCard;

public class MovementCardLook extends ElementLook {

    // TODO : BUG HERE

    public MovementCardLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 2);
    }

    protected void render() {
        MovementCard card = (MovementCard)element;

        if(card.isInStack()) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "M" + ConsoleColor.RESET;
        } else {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getDirection().getSymbole() + ConsoleColor.RESET;
            shape[1][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getStep() + ConsoleColor.RESET;
        }
    }

}
