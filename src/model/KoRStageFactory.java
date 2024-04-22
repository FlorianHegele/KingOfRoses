package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class KoRStageFactory extends StageElementsFactory {
    private KoRStageModel stageModel;

    public KoRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (HoleStageModel) gameStageModel;
    }

    @Override
    public void setup() {

        /*
        TODO:
            - complete this list
            - create the board, pots, pawns, cards and set them in the stage model
            - assign the pawns to their cells in the pots
         */
    }
}
