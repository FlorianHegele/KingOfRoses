package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.MovementCard;

public class MovementCardLook extends ElementLook {

    public MovementCardLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1);
    }

    protected void render() {
        MovementCard card = (MovementCard)element;

        shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getDirection().getSymbole() + card.getStep() + ConsoleColor.RESET;
    }

}
