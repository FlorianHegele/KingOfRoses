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
        ElementTypes.register("pawn",50);
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
    }

}