package model.element.card;

import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.animation.Animation;
import boardifier.model.animation.AnimationStep;
import javafx.scene.paint.Color;
import model.data.ElementType;
import model.data.PlayerData;


/**
 * Represents a movement card in the game.
 */
public class MovementCard extends GameElement {

    private boolean inverted;
    private final int step;
    private Direction movementDirection;
    private Direction visualDirection;
    private Owner owner;

    /**
     * Constructs a new movement card with the specified step, direction, and game stage model.
     *
     * @param step           The step of the movement card.
     * @param direction      The direction of the movement card.
     * @param gameStageModel The game stage model to which this movement card belongs.
     */
    public MovementCard(int step, Direction direction, GameStageModel gameStageModel) {
        super(gameStageModel);

        // Register new element type
        this.type = ElementType.MOVEMENT_CARD.register();

        this.step = step;
        this.movementDirection = direction;
        this.visualDirection = direction;
        this.inverted = false;
        this.owner = Owner.STACK;
    }

    /**
     * Gets the owner of the movement card.
     *
     * @return The owner of the movement card.
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the movement card and invert
     * the card if the owner is the red player.
     *
     * @param owner The owner to set.
     */

    public void setOwner(Owner owner) {
        this.owner = owner;
        if (owner == Owner.PLAYER_RED){
            toggleInverted();
        } else {
            addChangeFaceEvent();
        }
    }

    /**
     * Checks if the movement card is inverted.
     *
     * @return True if the movement card is inverted, false otherwise.
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * Toggles the inverted status of the movement card.
     */
    public void toggleInverted() {
        this.inverted = !this.inverted;
        this.movementDirection = movementDirection.getOpposite();
        addChangeFaceEvent();
    }

    /**
     * Gets the step of the movement card.
     *
     * @return The step of the movement card.
     */
    public int getStep() {
        return step;
    }

    /**
     * Gets the representation of the step as a character.
     *
     * @return The representation of the step as a character.
     */
    public char getStepRepresentation() {
        final int startPoint = 8543;
        if (step < 1 || step > 3) {
            throw new IllegalArgumentException("Invalid step");
        }
        return (char) (startPoint + step);
    }

    public void update() {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step == null) {
                animation = null;
            }
            else if (step == Animation.NOPStep) {
                Logger.debug("nothing to do", this);
            }
            else {
                Logger.debug("move animation", this);
                setLocation(step.getInt(0), step.getInt(1));
            }
        }
    }

    @Override
    public String toString() {
        return "MovementCard{" +
                "inverted=" + inverted +
                ", step=" + step +
                ", movementDirection=" + movementDirection +
                ", visualDirection=" + visualDirection +
                ", owner=" + owner +
                '}';
    }

    /**
     * Gets the visual direction of the movement card.
     *
     * @return The direction of the movement card.
     */
    public Direction getVisualDirection() {
        return visualDirection;
    }


    /**
     * Gets the direction vector of the movement card.
     *
     * @return The direction vector of the movement card.
     */
    public Coord2D getDirectionVector() {
        return movementDirection.getVector().multiply(step);
    }

    /**
     * Represents the direction of the movement card.
     */
    public enum Direction {
        NORTH(-1, 0, "north"),
        NORTHEAST(-1, 1, "northeast"),
        EAST(0, 1, "east"),
        SOUTHEAST(1, 1, "southeast"),
        SOUTH(1, 0, "south"),
        SOUTHWEST(1, -1, "southwest"),
        WEST(0, -1, "west"),
        NORTHWEST(-1, -1, "northwest");

        private final Coord2D vector;
        private final String path;

        /**
         * Constructs a new direction with the specified column, row, and symbol.
         *
         * @param col    The column of the direction.
         * @param row    The row of the direction.
         * @param path The symbol representing the direction.
         */
        Direction(int col, int row, String path) {
            this(new Coord2D(row, col), path);
        }

        /**
         * Constructs a new direction with the specified vector and symbol.
         *
         * @param vector The vector representing the direction.
         * @param path The symbol representing the direction.
         */
        Direction(Coord2D vector, String path) {
            this.path = path;
            this.vector = vector;
        }

        /**
         * Gets the direction vector.
         *
         * @return The direction vector.
         */
        public Coord2D getVector() {
            return vector;
        }

        /**
         * Gets the symbol representing the direction.
         *
         * @return The symbol representing the direction.
         */
        public String getPath(int step) {
            return "/movement_card/" + path + "_" + step + ".png";
        }

        /**
         * Gets the opposite direction.
         *
         * @return The opposite direction.
         */
        public Direction getOpposite() {
            final Direction opposite;
            switch (this) {
                case NORTH -> opposite = SOUTH;
                case NORTHEAST -> opposite = SOUTHWEST;
                case EAST -> opposite = WEST;
                case SOUTHEAST -> opposite = NORTHWEST;
                case SOUTH -> opposite = NORTH;
                case SOUTHWEST -> opposite = NORTHEAST;
                case WEST -> opposite = EAST;
                case NORTHWEST -> opposite = SOUTHEAST;
                default -> throw new IllegalCallerException("Illegal direction");
            }
            return opposite;
        }
    }

    /**
     * Represents the owner of the movement card.
     */
    public enum Owner {
        PLAYER_RED,
        PLAYER_BLUE,
        STACK,
        // (OUT means that the card doesn't belong to a player and isn't in the stack)
        OUT;

        /**
         * Gets the background color associated with the owner.
         *
         * @return The background color associated with the owner.
         */
        public Color getColor() {
            return (this == OUT) ? Color.YELLOW : Color.WHITE;
        }

        public boolean isCurrentPlayer(Model model) {
            return isSpecificPlayer(PlayerData.getCurrentPlayerData(model));
        }

        public boolean isSpecificPlayer(PlayerData playerData) {
            return (this == PLAYER_RED && playerData == PlayerData.PLAYER_RED) || (this == PLAYER_BLUE && playerData == PlayerData.PLAYER_BLUE);
        }

        public boolean isPlayer() {
            return this == PLAYER_BLUE || this == PLAYER_RED;
        }
    }
}