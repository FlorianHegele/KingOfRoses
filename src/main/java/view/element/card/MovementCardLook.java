package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.PlayerData;
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
        } else if (owner == MovementCard.Owner.OUT) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.YELLOW_BACKGROUND + card.getDirection().getSymbole() + ConsoleColor.RESET;
            shape[1][0] = ConsoleColor.BLACK + ConsoleColor.YELLOW_BACKGROUND + card.getStep() + ConsoleColor.RESET;
        } else if (owner == MovementCard.Owner.PLAYER_RED) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getDirection().getSymbole() + ConsoleColor.RESET;
            shape[1][0] = ConsoleColor.BLACK + PlayerData.PLAYER_RED.getBackgroundColor()  + card.getStep() + ConsoleColor.RESET;
        } else if (owner == MovementCard.Owner.PLAYER_BLUE) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getDirection().getSymbole() + ConsoleColor.RESET;
            shape[1][0] = ConsoleColor.BLACK + PlayerData.PLAYER_BLUE.getBackgroundColor()  + card.getStep() + ConsoleColor.RESET;
        }
    }

}
