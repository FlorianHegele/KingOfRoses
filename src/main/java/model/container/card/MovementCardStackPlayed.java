package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

/**
 * The MovementCardStackPlayed class represents a stack of movement cards that have been played.
 * It creates a 1x1 grid for the played stack.
 */
public class MovementCardStackPlayed extends ContainerElement {

    /**
     * Constructs a new MovementCardStackPlayed with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the played stack.
     * @param y              The y-coordinate of the played stack.
     * @param gameStageModel The game stage model associated with this played stack.
     */
    public MovementCardStackPlayed(int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 1x1 grid, named "movementcardstackplayed", at the specified coordinates
        super("movementcardstackplayed", x, y, 1, 1, gameStageModel);
    }
}