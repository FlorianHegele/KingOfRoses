package model.container.card;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

// FIXME : Bug pile (un/plusieurs élément(s) qui s'affiche(nt) en haut à gauche)
// Element affiché dans la pile "M"

public class MovementCardStack extends ContainerElement {

    public MovementCardStack(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 1x1 grid, named "movementcardstack", and in x,y in space
        super("movementcardstack", x, y, 1, 1, gameStageModel);
    }
}
