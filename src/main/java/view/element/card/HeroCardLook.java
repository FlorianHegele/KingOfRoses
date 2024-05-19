package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.HeroCard;

/**
 * The HeroCardLook class represents the visual representation (look) of a hero card element in the game.
 * It renders the hero card as a single character on a 1x1 grid.
 */
public class HeroCardLook extends ElementLook {

    /**
     * Constructs a new HeroCardLook for the specified hero card element.
     * @param element The hero card element to render.
     */
    public HeroCardLook(GameElement element) {
        // Hero card look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1);
    }

    /**
     * Renders the visual representation of the hero card based on its status.
     */
    protected void render() {
        HeroCard card = (HeroCard) element;

        // Set the shape character based on the hero card's background color
        shape[0][0] = ConsoleColor.BLACK + card.getStatus().getBackgroundColor() + "H" + ConsoleColor.RESET;
    }
}
