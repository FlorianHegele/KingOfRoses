package model.element;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;
import model.KoRStageModel;
import model.data.PlayerData;

/**
 * Represents a pawn game element.
 */
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
        // Associate the word "pawn" with the integer 50
        ElementTypes.register("pawn", 50);
        // Retrieve the integer associated with the word "pawn" and associate it with the type variable
        this.type = ElementTypes.getType("pawn");

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


    public boolean isUnderKing() {
        if (status == Status.KING_PAWN) return false;
        if (!getContainer().getName().equals("KoRboard")) return false;

        return ((KoRStageModel) gameStageModel).getKingPawn().getLocation().equals(getLocation());
    }

    /**
     * Enumerates the possible statuses of a pawn.
     */
    public enum Status {

        RED_PAWN(PlayerData.PLAYER_RED),
        BLUE_PAWN(PlayerData.PLAYER_BLUE),
        KING_PAWN(2, ConsoleColor.YELLOW_BACKGROUND);

        private final int id;
        private final String backgroundColor;

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
         * @param backgroundColor The background color associated with the status.
         */
        Status(int id, String backgroundColor) {
            this.id = id;
            this.backgroundColor = backgroundColor;
        }

        /**
         * Retrieves the background color associated with the status.
         *
         * @return The background color associated with the status.
         */
        public String getBackgroundColor() {
            return backgroundColor;
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
