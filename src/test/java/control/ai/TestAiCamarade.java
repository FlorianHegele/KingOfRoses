package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import javafx.stage.Stage;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.data.AIData;
import model.data.PlayerData;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.ActionsUtils;
import utils.Boardifiers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAiCamarade{

    private KoRStageModel stageModel;
    private Controller controller;


    @BeforeEach
    void createStageModel() throws GameException {
        // Create the model
        final Model model = new Model();
        final Stage stage = Mockito.mock(Stage.class);

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, GameConfigurationModel.RANDOM_SEED, 2,
                0, false, true);
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, AIData.CAMARADE, PlayerData.PLAYER_RED, AIData.CAMARADE));

        // Init and start the Game
        final Boardifiers boardifiers = new Boardifiers(stage, model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.getController().startGame();

        controller = boardifiers.getController();
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

        // Remove a card from the red AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(controller, stageModel.getModel(), movementCard2, stageModel.getMovementCardStackPlayed().getName(), 0, 0));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();

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
        final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_RED).get(0);

        // Remove a card from the red AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(controller, stageModel.getModel(), movementCard, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionL));
    }

}