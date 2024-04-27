package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;


public class HeroCard extends GameElement {

    private final int step;
    private final Direction direction;

    public HeroCard(int step, Direction direction, GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        ElementTypes.register("hero_card",52);
        this.type = ElementTypes.getType("hero_card");

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