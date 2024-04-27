package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class MovementCard extends GameElement {

    private final int step;
    private final Direction direction;

    public MovementCard(int step, Direction direction, GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        ElementTypes.register("direction_card",51);
        this.type = ElementTypes.getType("direction_card");

        this.step = step;
        this.direction = direction;
    }

    public int getStep() {
        return step;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
    }
}