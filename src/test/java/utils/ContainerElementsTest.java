package utils;

import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.Model;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.data.ElementType;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerElementsTest {

    private KoRStageModel stageModel;
    private Model model;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);
        stageModel.getDefaultElementFactory().setup();
    }

    @Test
    void testCountElements() {
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
        assertEquals(26, ContainerElements.countElements(stageModel.getBluePot()));
    }

    @Test
    void testOutside() {
        final ContainerElement container = new ContainerElement("container", 0, 0, 3, 3, stageModel);
        assertTrue(ContainerElements.isOutside(container, -1, 0));
        assertTrue(ContainerElements.isOutside(container, 0, -1));
        assertTrue(ContainerElements.isOutside(container, 3, 0));
        assertTrue(ContainerElements.isOutside(container, 0, 3));
    }

    @Test
    void testInside() {
        final ContainerElement container = new ContainerElement("container", 0, 0, 3, 3, stageModel);
        assertFalse(ContainerElements.isOutside(container, 0, 1));
        assertFalse(ContainerElements.isOutside(container, 1, 0));
        assertFalse(ContainerElements.isOutside(container, 2, 2));
        assertFalse(ContainerElements.isOutside(container, 0, 0));
    }

    @Test
    void testSelectedElement() {
        MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0);
        movementCard.toggleSelected();

        MovementCard movementCardSelected = ContainerElements.getSelectedElement(stageModel, ElementType.MOVEMENT_CARD);
        assertEquals(movementCardSelected, movementCard);
    }

    @Test
    void testNotSelectedElement() {
        assertThrows(IllegalCallerException.class, () -> ContainerElements.getSelectedElement(stageModel, ElementType.MOVEMENT_CARD));
    }

    @Test
    void testGetEmptyPosition() {
        final MovementCardSpread movementCardSpread = stageModel.getBlueMovementCardsSpread();

        assertNull(ContainerElements.getEmptyPosition(movementCardSpread));
        for(int i=4; i>=0; i--) {
            final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(i);
            movementCard.removeFromStage();
            assertEquals(new Coord2D(i, 0), ContainerElements.getEmptyPosition(movementCardSpread));
        }
    }

    @Test
    void testGetElementPosition() {
        final MovementCardSpread movementCardSpread = stageModel.getBlueMovementCardsSpread();

        assertNull(ContainerElements.getElementPosition(new MovementCard(-1, MovementCard.Direction.NORTH, stageModel), movementCardSpread));
        for(int i=0; i<5; i++) {
            final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(i);
            assertEquals(new Coord2D(i, 0), ContainerElements.getElementPosition(movementCard, movementCardSpread));
        }
    }

}
