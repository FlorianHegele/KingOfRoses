package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import model.PlayerData;

public class MovementCardSpread extends ContainerElement {

    public static final String PREFIX = "movementcardspread_";

    public MovementCardSpread(PlayerData playerData, int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 5x1 grid, named "movementcardspread", and in x,y in space
        super(PREFIX + playerData.getName(), x, y, 5, 1, gameStageModel);
    }

}
