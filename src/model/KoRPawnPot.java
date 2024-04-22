package model;

import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

public class KoRPawnPot extends ContainerElement {
    // TODO : Adapt the constructor to the new size of the pawn pot
    public KoRPawnPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 4x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, 4, 1, gameStageModel);
    }
}
