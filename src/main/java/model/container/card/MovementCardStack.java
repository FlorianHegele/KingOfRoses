package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

/**
 * The MovementCardStack class represents a stack of movement cards.
 * It creates a 1x1 grid for the stack.
 */
public class MovementCardStack extends ContainerElement {

    /**
     * Constructs a new MovementCardStack with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the stack.
     * @param y              The y-coordinate of the stack.
     * @param gameStageModel The game stage model associated with this stack.
     */
    public MovementCardStack(int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 1x1 grid, named "movementcardstack", at the specified coordinates
        super("movementcardstack", x, y, 1, 1, gameStageModel);
    }
}