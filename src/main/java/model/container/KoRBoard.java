package model.container;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

/**
 * KoR main board represent the element where pawns are put when played
 * Thus, a simple ContainerElement with 9 rows and 9 column is needed.
 * Nevertheless, in order to "simplify" the work for the controller part,
 * this class also contains method to determine all the valid cells to put a
 * pawn with a given value.
 */
public class KoRBoard extends ContainerElement {
    public KoRBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 9x9 grid, named "KoR", and in x,y in space
        super("KoRboard", x, y, 9, 9, gameStageModel);
    }
}
