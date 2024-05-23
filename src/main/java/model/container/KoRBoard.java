package model.container;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

/**
 * The KoR main board represents the element where pawns are placed when played.
 * This class provides a 9x9 grid to accommodate pawns.
 */
public class KoRBoard extends ContainerElement {

    /**
     * Constructs a new KoRBoard with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the board.
     * @param y              The y-coordinate of the board.
     * @param gameStageModel The game stage model associated with this board.
     */
    public KoRBoard(int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 9x9 grid, named "KoR", at the specified coordinates
        super("KoRboard", x, y, 9, 9, gameStageModel);
    }
}
