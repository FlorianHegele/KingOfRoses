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

        BLUE_PAWN(ConsoleColor.BLUE_BACKGROUND),
        RED_PAWN(ConsoleColor.RED_BACKGROUND),
        KING_PAWN(ConsoleColor.YELLOW_BACKGROUND);

        private final String backgroundColor;

        Status(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public Status getOpposite() {
            return (this == BLUE_PAWN) ? RED_PAWN : BLUE_PAWN;
        }

    }

}