package model.element.card;

import boardifier.model.GameStageModel;
import model.KoRStageModel;
import model.element.card.MovementCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovementCardTest {

    @Test
    void testInvertDirection() {
        // CHECK IF THE INVERTED DIRECTION IS WORKING WELL
        assertEquals(MovementCard.Direction.SOUTH, MovementCard.Direction.NORTH.getOpposite());
        assertEquals(MovementCard.Direction.NORTH, MovementCard.Direction.SOUTH.getOpposite());
        assertEquals(MovementCard.Direction.WEST, MovementCard.Direction.EAST.getOpposite());
        assertEquals(MovementCard.Direction.EAST, MovementCard.Direction.WEST.getOpposite());
        assertEquals(MovementCard.Direction.NORTHEAST, MovementCard.Direction.SOUTHWEST.getOpposite());
        assertEquals(MovementCard.Direction.SOUTHWEST, MovementCard.Direction.NORTHEAST.getOpposite());
        assertEquals(MovementCard.Direction.SOUTHEAST, MovementCard.Direction.NORTHWEST.getOpposite());
        assertEquals(MovementCard.Direction.NORTHWEST, MovementCard.Direction.SOUTHEAST.getOpposite());
    }

    @Test
    void testToggleMovementCard() {
        final GameStageModel gameStageModel = new KoRStageModel("", null);
        // SETUP MOVEMENT CARD WITH OPPOSITE DIRECTION
        final MovementCard movementCard = new MovementCard(1, MovementCard.Direction.NORTH, gameStageModel);
        final MovementCard movementCardInvert = new MovementCard(1, MovementCard.Direction.SOUTH, gameStageModel);
        // INVERT ONE CARD
        movementCardInvert.toggleInverted();

        // CHECK IF THE MOVEMENT CARD IS INVERTED IN THE RIGHT DIRECTION
        assertTrue(movementCardInvert.isInverted());
        assertEquals(movementCard.getVisualDirection().getVector(), movementCardInvert.getDirectionVector());
    }

    @Test
    void testToggleMovementCardByOwner() {
        final GameStageModel gameStageModel = new KoRStageModel("", null);
        // SETUP MOVEMENT CARD
        final MovementCard movementCardBlue = new MovementCard(1, MovementCard.Direction.NORTH, gameStageModel);
        final MovementCard movementCardRed = new MovementCard(1, MovementCard.Direction.SOUTH, gameStageModel);
        // CHANGE THEIR OWNER
        movementCardBlue.setOwner(MovementCard.Owner.PLAYER_BLUE);
        movementCardRed.setOwner(MovementCard.Owner.PLAYER_RED);

        // CHECK IF BLUE MOVEMENT CARD IS NOT INVERTED AND IF RED MOVEMENT CARD IS INVERTED
        assertEquals(MovementCard.Direction.NORTH.getVector(), movementCardBlue.getDirectionVector());
        assertEquals(MovementCard.Direction.NORTH.getVector(), movementCardRed.getDirectionVector());
    }

    @Test
    void testDefaultOwnerMovementCard() {
        final GameStageModel gameStageModel = new KoRStageModel("", null);
        // SETUP MOVEMENT CARD
        final MovementCard movementCard = new MovementCard(1, MovementCard.Direction.NORTH, gameStageModel);
        assertEquals(MovementCard.Owner.STACK, movementCard.getOwner());
    }
}
