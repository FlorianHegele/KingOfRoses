package model.element.card;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import javafx.scene.paint.Color;
import model.data.ElementType;
import model.data.PlayerData;

/**
 * Represents a hero card in the game.
 */
public class HeroCard extends GameElement {

    private final Status status;

    /**
     * Constructs a new hero card with the specified status and game stage model.
     *
     * @param status         The status of the hero card.
     * @param gameStageModel The game stage model to which this hero card belongs.
     */
    public HeroCard(Status status, GameStageModel gameStageModel) {
        super(gameStageModel);

        // Register new element type
        this.type = ElementType.HERO_CARD.register();

        this.status = status;
    }

    /**
     * Gets the status of the hero card.
     *
     * @return The status of the hero card.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Represents the status of a hero card.
     */
    public enum Status {

        BLUE_CARD(PlayerData.PLAYER_BLUE),
        RED_CARD(PlayerData.PLAYER_RED);

        private final int id;
        private final Color color;

        /**
         * Constructs a new status with the specified player data.
         *
         * @param playerData The player data associated with this status.
         */
        Status(PlayerData playerData) {
            this(playerData.getId(), playerData.getBackgroundColor());
        }

        /**
         * Constructs a new status with the specified ID and background color.
         *
         * @param id              The ID of the status.
         * @param color The color associated with this status.
         */
        Status(int id, Color color) {
            this.id = id;
            this.color = color;
        }

        /**
         * Gets the ID of the status.
         *
         * @return The ID of the status.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the background color associated with this status.
         *
         * @return The background color of the status.
         */
        public Color getColor() {
            return color;
        }

        public boolean isOwnedBy(PlayerData playerData) {
            return playerData.getId() == id;
        }
    }

}