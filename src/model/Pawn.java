package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import java.awt.*;


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

        BLUE_PAWN(Color.BLUE),
        RED_PAWN(Color.RED),
        KING_PAWN(Color.YELLOW);

        private final Color color;

        Status(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public Status getOpposite() {
            return (this == BLUE_PAWN) ? RED_PAWN : BLUE_PAWN;
        }

    }

}