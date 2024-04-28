package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class MovementCardSpread extends ContainerElement {

    public MovementCardSpread(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 5x1 grid, named "movementcardspread", and in x,y in space
        super("movementcardspread", x, y, 1, 5, gameStageModel);
    }
}
