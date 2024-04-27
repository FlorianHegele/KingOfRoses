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
        NORTH("\u2191"),
        NORTHEAST("\u2197"),
        EAST("\u2192"),
        SOUTHEAST("\u2198"),
        SOUTH("\u2193"),
        SOUTHWEST("\u2199"),
        WEST("\u2190"),
        NORTHWEST("\u2196");

        private final String symbole;

        Direction(String symbole) {
            this.symbole = symbole;
        }

        public String getSymbole() {
            return symbole;
        }
    }
}