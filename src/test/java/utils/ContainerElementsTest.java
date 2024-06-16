package utils;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.StageFactory;
import boardifier.model.Coord2D;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.KoRController;
import javafx.stage.Stage;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import utils.Boardifiers;
import utils.ContainerElements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContainerElementsTest {

    private KoRStageModel stageModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create the model
        Model model = new Model();
        final Stage stage = Mockito.mock(Stage.class);

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, 3, 2,
                0, false, true);

        // Init and start the Game
        final Boardifiers boardifiers = new Boardifiers(stage, model, gameConfigurationModel);
        boardifiers.getController().startGame();

        stageModel = (KoRStageModel) model.getGameStage();
    }

    @Test
    void testCountElements() {
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
        assertEquals(26, ContainerElements.countElements(stageModel.getBluePot()));
    }

    @Test
    void testGetEmptyPosition() {
        final Model model = stageModel.getModel();
        final MovementCardSpread movementCardSpread = stageModel.getBlueMovementCardsSpread();

        assertNull(ContainerElements.getEmptyPosition(movementCardSpread));
        for(int i=4; i>=0; i--) {
            final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(i);
            new ActionPlayer(model, null, ActionFactory.generateRemoveFromStage(model, movementCard)).start();
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
