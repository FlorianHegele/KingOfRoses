package model.element.card;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;
import model.PlayerData;


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

        BLUE_CARD(PlayerData.PLAYER_BLUE),
        RED_CARD(PlayerData.PLAYER_RED);

        private final int id;
        private final String backgroundColor;

        Status(PlayerData playerData) {
            this(playerData.getId(), playerData.getBackgroundColor());
        }

        Status(int id, String backgroundColor) {
            this.id = id;
            this.backgroundColor = backgroundColor;
        }

        public int getId() {
            return id;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

    }

}