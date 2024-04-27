package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;


public class HeroCard extends GameElement {

    private final Status status;

    public HeroCard(Status status, GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        ElementTypes.register("hero_card",52);
        this.type = ElementTypes.getType("hero_card");

        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {

        BLUE_CARD(ConsoleColor.BLUE_BACKGROUND),
        RED_CARD(ConsoleColor.RED_BACKGROUND);

        private final String backgroundColor;

        Status(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

    }

}