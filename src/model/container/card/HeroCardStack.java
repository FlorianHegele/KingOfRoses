package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class HeroCardStack extends ContainerElement {

    public HeroCardStack(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "cardstack", and in x,y in space
        super("cardstack", x, y, 1, 2, gameStageModel);
    }
}
