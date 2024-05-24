package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import control.ConsoleController;
import control.GameConfigurationController;
import control.KoRController;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.data.AIData;
import model.data.PlayerData;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ActionsUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestAiCamarade{

    private KoRStageModel stageModel;
    private GameConfigurationModel gameconfigurationModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(
                model,
                2,
                GameConfigurationModel.DEFAULT_LOGGER_MODE,
                GameConfigurationModel.DEFAULT_PLAYER_INTERACTION,
                false);
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // Load game elements
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        // Change AI of the first player to CAMARADE for the test needs
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, AIData.CAMARADE, PlayerData.PLAYER_RED, AIData.CAMARADE));

        control.setFirstStageName("kor");

        control.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
    }

    @Test
    void testPlateauVide(){

        // Create the AI decider
        KoRDeciderCamarade aiDecider = new KoRDeciderCamarade(stageModel.getModel(), null, PlayerData.PLAYER_RED);

        // Get the action list from the AI
        ActionList actionList = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionList));

    }

    @Test
    void testPeutPiocherOuJouerSurPlateauVide(){
        // Create the AI decider
        KoRDeciderCamarade aiDecider = new KoRDeciderCamarade(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final MovementCard movementCard1 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0);
        final MovementCard movementCard2 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1);
        final MovementCard movementCard3 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(2);
        final MovementCard movementCard4 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(3);
        final MovementCard movementCard5 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(4);
        System.out.println("Got this from blue player hand : " + "\n ¤ " + movementCard1 + "\n ¤ " + movementCard2 + "\n ¤ " + movementCard3 + "\n ¤ " + movementCard4 + "\n ¤ " + movementCard5);

        // Remove a card from the red AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(stageModel.getModel(), movementCard2, stageModel.getMovementCardStackPlayed().getName(), 0, 0));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();
        System.out.println("New blue player hand is : " + "\n ¤ " + movementCard1 + "\n ¤ " + movementCard2 + "\n ¤ " + movementCard3 + "\n ¤ " + movementCard4 + "\n ¤ " + movementCard5);

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVide(){
        // Create the AI decider
        KoRDeciderCamarade aiDecider = new KoRDeciderCamarade(stageModel.getModel(), null, PlayerData.PLAYER_RED);

        // Get the AI cards
        final MovementCardSpread redMoveCardsHand = stageModel.getRedMovementCardsSpread();
        System.out.println("Red player first card is : " + redMoveCardsHand.getElement(0,0));
        final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_RED).get(0);
        System.out.println("Got this from red player hand : " + movementCard);

        // Remove a card from the red AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(stageModel.getModel(), movementCard, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        new ActionPlayer(stageModel.getModel(), null, actionList).start();
        System.out.println("Red player first card is : " + redMoveCardsHand.getElement(0,0));

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionL));
    }

}