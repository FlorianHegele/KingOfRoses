package model.element.card;

import boardifier.model.Coord2D;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class MovementCard extends GameElement {

    private final int step;
    private final Direction direction;
    private boolean isInStack;

    public MovementCard(int step, Direction direction, GameStageModel gameStageModel) {
        super(gameStageModel);

        // REGISTER NEW ELEMENT TYPE
        ElementTypes.register("direction_card",51);
        this.type = ElementTypes.getType("direction_card");

        this.step = step;
        this.direction = direction;
        this.isInStack = true;
    }

    public boolean isInStack() {
        return isInStack;
    }

    public void setInStack(boolean isInStack) {
        this.isInStack = isInStack;
    }

    public int getStep() {
        return step;
    }

    public char getStepRepresentation() {
        final int startPoint = 8543;
        if (step < 1 || step > 3) {
            throw new IllegalArgumentException("Invalid step");
        }
        return (char) (startPoint + step);
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        NORTH(1, 0, "\u2191"),
        NORTHEAST(1, 1, "\u2197"),
        EAST(0, 1, "\u2192"),
        SOUTHEAST(1, -1, "\u2198"),
        SOUTH(0, -1, "\u2193"),
        SOUTHWEST(-1, -1, "\u2199"),
        WEST(-1, 1, "\u2190"),
        NORTHWEST(-1, -1, "\u2196");

        private final Coord2D vecteur;
        private final String symbole;

        Direction(int dx, int dy, String symbole) {
            this(new Coord2D(dx, dy), symbole);
        }

        Direction(Coord2D vecteur, String symbole) {
            this.symbole = symbole;
            this.vecteur = vecteur;
        }

        public Coord2D getVecteur() {
            return vecteur;
        }

        public String getSymbole() {
            return symbole;
        }

        public Direction getOpposite() {
            switch (this) {
                case NORTH -> {
                    return SOUTH;
                }

                case NORTHEAST -> {
                    return SOUTHWEST;
                }

                case EAST -> {
                    return WEST;
                }

                case SOUTHEAST -> {
                    return NORTHWEST;
                }

                case SOUTH -> {
                    return NORTH;
                }

                case SOUTHWEST -> {
                    return NORTHEAST;
                }

                case WEST -> {
                    return EAST;
                }

                case NORTHWEST -> {
                    return SOUTHEAST;
                }

                default -> throw new IllegalCallerException("Illegal direction");
            }
        }
    }
}