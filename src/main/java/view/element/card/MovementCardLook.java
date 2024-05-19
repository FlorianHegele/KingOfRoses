package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.MovementCard;

/**
 * The MovementCardLook class represents the visual representation (look) of a movement card element in the game.
 * It renders the movement card with its direction and step information on a 1x2 grid.
 */
public class MovementCardLook extends ElementLook {

    /**
     * Constructs a new MovementCardLook for the specified movement card element.
     * @param element The movement card element to render.
     */
    public MovementCardLook(GameElement element) {
        // Movement card look is constituted of direction and step, so shape size = 1x2
        super(element, 1, 2);
        setAnchorType(ElementLook.ANCHOR_TOPLEFT);
    }

    /**
     * Renders the visual representation of the movement card based on its owner and properties.
     */
    protected void render() {
        MovementCard card = (MovementCard) element;

        final MovementCard.Owner owner = card.getOwner();
        if (owner == MovementCard.Owner.STACK) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "P" + ConsoleColor.RESET;
            shape[1][0] = " ";
        } else {
            final String backgroundColor = card.getOwner().getBackgroundColor();
            final String direction = ConsoleColor.BLACK + backgroundColor + card.getDirection().getSymbol() + ConsoleColor.RESET;
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

