package model.element;

import boardifier.control.Logger;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.Animation;
import boardifier.model.animation.AnimationStep;
import javafx.scene.paint.Color;
import model.KoRStageModel;
import model.data.ElementType;
import model.data.PlayerData;

public class Pawn extends GameElement {


    private Status status;
    /**
     * Constructs a pawn with the specified status and game stage model.
     *
     * @param status         The status of the pawn.
     * @param gameStageModel The game stage model to which the pawn belongs.
     */
    public Pawn(Status status, GameStageModel gameStageModel) {
        super(gameStageModel);

        // Register new element type
        this.type = ElementType.PAWN.register();

        this.status = status;
    }

    /**
     * Flips the status of the pawn.
     */
    public void flipStatus() {
        this.status = this.status.getOpposite();
    }

    /**
     * Retrieves the status of the pawn.
     *
     * @return The status of the pawn.
     */
    public Status getStatus() {
        return status;
    }

    public void update() {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step == null) {
                animation = null;
            }
            else if (step == Animation.NOPStep) {
                Logger.debug("nothing to do", this);
            }
            else {
                Logger.debug("move animation", this);
                setLocation(step.getInt(0), step.getInt(1));
            }
        }
    }

    /**
     * Enumerates the possible statuses of a pawn.
     */
    public enum Status {

        RED_PAWN(PlayerData.PLAYER_RED),
        BLUE_PAWN(PlayerData.PLAYER_BLUE),
        KING_PAWN(2, Color.YELLOW);

        private final int id;
        private final Color color;

        /**
         * Constructs a status with the specified player data.
         *
         * @param playerData The player data associated with the status.
         */
        Status(PlayerData playerData) {
            this(playerData.getId(), playerData.getBackgroundColor());
        }

        /**
         * Constructs a status with the specified ID and background color.
         *
         * @param id              The ID associated with the status.
         * @param color The background color associated with the status.
         */
        Status(int id, Color color) {
            this.id = id;
            this.color = color;
        }

        /**
         * Retrieves the background color associated with the status.
         *
         * @return The background color associated with the status.
         */
        public Color getColor() {
            return color;
        }

        /**
         * Retrieves the opposite status.
         *
         * @return The opposite status.
         * @throws IllegalStateException If the status is KING_PAWN, which does not have an opposite status.
         */
        public Status getOpposite() {
            final Status opposite;
            switch (this) {
                case BLUE_PAWN -> opposite = RED_PAWN;
                case RED_PAWN -> opposite = BLUE_PAWN;
                default -> throw new IllegalCallerException("King pawn does not have an opposite status.");
            }
            return opposite;
        }

        /**
         * Retrieves the ID associated with the status.
         *
         * @return The ID associated with the status.
         */
        public int getID() {
            return id;
        }

        /**
         * Retrieves the pawn status associated with the specified player data.
         *
         * @param playerData The player data for which to retrieve the pawn status.
         * @return The pawn status associated with the specified player data.
         */
        public static Pawn.Status getPawnStatus(PlayerData playerData) {
            return (playerData == PlayerData.PLAYER_RED) ? Pawn.Status.RED_PAWN : Pawn.Status.BLUE_PAWN;
        }

        /**
         * Checks if the status is owned by the specified player data.
         *
         * @param playerData The player data to check against.
         * @return True if the status is owned by the player data, false otherwise.
         */
        public boolean isOwnedBy(PlayerData playerData) {
            return playerData.getId() == id;
        }
    }
}
