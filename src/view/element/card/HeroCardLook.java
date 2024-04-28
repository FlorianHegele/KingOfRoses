package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.element.card.HeroCard;

public class HeroCardLook extends ElementLook {

    public HeroCardLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 1, 1);
    }

    protected void render() {
        HeroCard card = (HeroCard)element;

        shape[0][0] = card.getStatus().getBackgroundColor() + ConsoleColor.RESET;
    }

}
