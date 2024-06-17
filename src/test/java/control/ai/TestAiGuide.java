package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.data.PlayerData;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ActionsUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAiGuide{

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
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the action list from the AI
        ActionList actionList = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(0, ActionsUtils.actionListToInt(actionList));
    }

    @Test
    void testPeutPiocherOuJouerSurPlateauVide(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

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
        assertEquals(0,ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVideUnderThreshold(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final MovementCard movementCard1 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0);
        final MovementCard movementCard2 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1);
        final MovementCard movementCard3 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(2);
        final MovementCard movementCard4 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(3);
        final MovementCard movementCard5 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(4);


        // Remove a card from the blue AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard1, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard2, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard3, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard4, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard5, stageModel.getMovementCardStackPlayed().getName(), 0, 0));


        // Put pawns on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(null, stageModel.getModel(), stageModel.getKingPawn(), 6, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 1, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 2, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 4, 4));


        // Give cards to the blue AI
        final MovementCard newMovementCard1 = new MovementCard(1, MovementCard.Direction.NORTH, stageModel);
        final MovementCard newMovementCard2 = new MovementCard(1, MovementCard.Direction.SOUTH, stageModel);
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), newMovementCard1 ,stageModel.getBlueMovementCardsSpread().getName(),0,0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), newMovementCard2 ,stageModel.getBlueMovementCardsSpread().getName(),0,1));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(0,ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVideOverThreshold(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final MovementCard movementCard1 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0);
        final MovementCard movementCard2 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1);
        final MovementCard movementCard3 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(2);
        final MovementCard movementCard4 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(3);
        final MovementCard movementCard5 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(4);


        // Remove all cards from the blue AI
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard1, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard2, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard3, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard4, stageModel.getMovementCardStackPlayed().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), movementCard5, stageModel.getMovementCardStackPlayed().getName(), 0, 0));


        // Put pawns on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(null, stageModel.getModel(), stageModel.getKingPawn(), 6, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 1, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 2, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 4, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 5, 4));


        // Give cards to the blue AI
        final MovementCard newMovementCard1 = new MovementCard(1, MovementCard.Direction.NORTH, stageModel);
        final MovementCard newMovementCard2 = new MovementCard(1, MovementCard.Direction.SOUTH, stageModel);
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), newMovementCard1 ,stageModel.getBlueMovementCardsSpread().getName(),0,0));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), newMovementCard2 ,stageModel.getBlueMovementCardsSpread().getName(),0,1));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI playing a movement card ?
        assertEquals(0,ActionsUtils.actionListToInt(actionL));
    }

}