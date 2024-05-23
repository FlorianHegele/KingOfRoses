package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import model.data.PlayerData;

/**
 * The MovementCardSpread class represents a spread of movement cards.
 * It creates a 1x5 grid for the spread.
 */
public class MovementCardSpread extends ContainerElement {

    /**
     * The prefix for the name of the spread.
     */
    public static final String PREFIX = "movementcardspread_";

    /**
     * Constructs a new MovementCardSpread with the specified player data, coordinates, and associated game stage model.
     *
     * @param playerData     The player data associated with this spread.
     * @param x              The x-coordinate of the spread.
     * @param y              The y-coordinate of the spread.
     * @param gameStageModel The game stage model associated with this spread.
     */
    public MovementCardSpread(PlayerData playerData, int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 1x5 grid, named "movementcardspread", at the specified coordinates
        super(PREFIX + playerData.getName(), x, y, 1, 5, gameStageModel);
    }

}