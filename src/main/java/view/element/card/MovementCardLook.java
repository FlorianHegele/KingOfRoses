package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.MovementCard;

public class MovementCardLook extends ElementLook {

    public MovementCardLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 2);
        setAnchorType(ElementLook.ANCHOR_TOPLEFT);
    }

    protected void render() {
        MovementCard card = (MovementCard) element;

        final MovementCard.Owner owner = card.getOwner();
        if (owner == MovementCard.Owner.STACK) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "M" + ConsoleColor.RESET;
            shape[1][0] = " ";
        }

        else {
            final String backgroundColor = card.getOwner().getBackgroundColor();
            final String direction = ConsoleColor.BLACK + backgroundColor + card.getDirection().getSymbole() + ConsoleColor.RESET;
            final String step = ConsoleColor.BLACK + backgroundColor + card.getStep() + ConsoleColor.RESET;

            if(card.isInverted()) {
                shape[0][0] = step;
                shape[1][0] = direction;
            } else {
                shape[0][0] = direction;
                shape[1][0] = step;
            }
        }
    }

}
