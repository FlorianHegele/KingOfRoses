package model.element;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;


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

        RED_PAWN(ConsoleColor.RED_BACKGROUND, 0),
        BLUE_PAWN(ConsoleColor.BLUE_BACKGROUND, 1),
        KING_PAWN(ConsoleColor.YELLOW_BACKGROUND, 2);

        private final String backgroundColor;
        private final int id;

        Status(String backgroundColor, int id) {
            this.backgroundColor = backgroundColor;
            this.id = id;
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