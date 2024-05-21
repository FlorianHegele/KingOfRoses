package utils;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.StageFactory;
import boardifier.model.*;
import boardifier.view.View;
import control.ConsoleController;
import control.GameConfigurationController;
import control.KoRController;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContainerElementsTest {

    private KoRStageModel stageModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model);
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // Load game elements
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        control.setFirstStageName("kor");

        control.startGame();

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
