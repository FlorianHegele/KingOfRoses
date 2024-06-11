package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

/**
 * The HeroCardStack class represents a stack of hero cards.
 * It creates a 1x1 grid for the stack.
 */
public class HeroCardStack extends ContainerElement {

    /**
     * Constructs a new HeroCardStack with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the stack.
     * @param y              The y-coordinate of the stack.
     * @param gameStageModel The game stage model associated with this stack.
     */
    public HeroCardStack(int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 1x1 grid, named "herocardstack", at the specified coordinates
        super("herocardstack", x, y, 1, 1, gameStageModel);
    }
}
