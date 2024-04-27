package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;


public class HeroCard extends GameElement {

    public HeroCard(GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        ElementTypes.register("hero_card",52);
        this.type = ElementTypes.getType("hero_card");
    }

}