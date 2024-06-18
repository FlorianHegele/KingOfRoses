package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.data.PlayerData;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ActionsUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAiCamarade{

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
        KoRDeciderCamarade aiDecider = new KoRDeciderCamarade(stageModel.getModel(), null, PlayerData.PLAYER_RED);

        // Get the action list from the AI
        ActionList actionList = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionList));
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
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard2, stageModel.getMovementCardStackPlayed().getName(), 0, 0));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionL));
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
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(1, ActionsUtils.actionListToInt(actionL));
    }

}