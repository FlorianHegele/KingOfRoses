package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import control.KoRController;
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

class TestAiHateCards{

    private KoRStageModel stageModel;
    private Model model;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);
        model.setGameStage(stageModel);
        stageModel.getDefaultElementFactory().setup();
    }

    @Test
    void testPlateauVide(){
        // Create the AI decider
        KoRDeciderHateCards aiDecider = new KoRDeciderHateCards(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the action list from the AI
        ActionList actionList = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionList));
    }

    @Test
    void testPeutPiocherOuJouerSurPlateauVide(){
        // Create the AI decider
        KoRDeciderHateCards aiDecider = new KoRDeciderHateCards(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final GameElement movementCard =  stageModel.getBlueMovementCardsSpread().getElement(0,0);
        // Remove a card from the red AI
        movementCard.removeFromStage();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVide(){
        // Create the AI decider
        KoRDeciderHateCards aiDecider = new KoRDeciderHateCards(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final GameElement movementCard =  stageModel.getBlueMovementCardsSpread().getElement(0,0);

        // Remove a card from the red AI
        movementCard.removeFromStage();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testAucuneCarteEnMain(){
        // Create the AI decider
        KoRDeciderHateCards aiDecider = new KoRDeciderHateCards(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final GameElement movementCard =  stageModel.getBlueMovementCardsSpread().getElement(0,0);
        final MovementCard movementCard2 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1);
        final MovementCard movementCard3 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(2);
        final MovementCard movementCard4 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(3);
        final MovementCard movementCard5 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(4);

        // Remove a card from the red AI
        final ActionList actionList = new ActionList();
        movementCard.removeFromStage();
        movementCard2.removeFromStage();
        movementCard3.removeFromStage();
        movementCard4.removeFromStage();
        movementCard5.removeFromStage();
        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(2, ActionsUtils.actionListToInt(actionL));
    }

}