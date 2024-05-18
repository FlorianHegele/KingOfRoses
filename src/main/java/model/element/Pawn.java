package model.element;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;
import model.PlayerData;


public class Pawn extends GameElement {

    private Status status;

    public Pawn(Status status, GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        // Associe le mot pawn à l'entier 50
        ElementTypes.register("pawn", 50);
        // Récupère l'entier associé au mot pawn et l'associe à la variable type
        this.type = ElementTypes.getType("pawn");

        this.status = status;
    }

    public void flipStatus() {
        this.status = this.status.getOpposite();
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {

        RED_PAWN(PlayerData.PLAYER_RED),
        BLUE_PAWN(PlayerData.PLAYER_BLUE),
        KING_PAWN(2, ConsoleColor.YELLOW_BACKGROUND);

        private final int id;
        private final String backgroundColor;

        Status(PlayerData playerData) {
            this(playerData.getId(), playerData.getBackgroundColor());
        }

        Status(int id, String backgroundColor) {
            this.id = id;
            this.backgroundColor = backgroundColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public Status getOpposite() {
            return (this == BLUE_PAWN) ? RED_PAWN : BLUE_PAWN;
        }

        public int getID() {
            return id;
        }

        public static Pawn.Status getPawnStatus(PlayerData playerData) {
            return (playerData == PlayerData.PLAYER_RED) ? Pawn.Status.RED_PAWN : Pawn.Status.BLUE_PAWN;
        }

        public boolean isOwnedBy(PlayerData playerData) {
            return playerData.getId() == id;
        }
    }

}
