package model.container;

import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

/**
 * Represents a pot for storing pawns in the game.
 */
public class PawnPot extends ContainerElement {

    /**
     * Constructs a new PawnPot with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the pot.
     * @param y              The y-coordinate of the pot.
     * @param gameStageModel The game stage model associated with this pot.
     */
    public PawnPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, 1, 1, gameStageModel);
    }
}