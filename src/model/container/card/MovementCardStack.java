package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class MovementCardStack extends ContainerElement {

    public MovementCardStack(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "movementcardstack", and in x,y in space
        super("movementcardstack", x, y, 24, 1, gameStageModel);
    }
}
