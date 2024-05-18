package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;


public class MovementCardStackPlayed extends ContainerElement {

    public MovementCardStackPlayed(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "movementcardstack", and in x,y in space
        super("movementcardstackplayed", x, y, 1, 1, gameStageModel);
    }
}
