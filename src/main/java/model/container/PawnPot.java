package model.container;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class PawnPot extends ContainerElement {

    public PawnPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, 1, 1, gameStageModel);
    }
}
