package control.ai;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.GameElement;
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
        assertEquals(1, ActionsUtils.actionListToInt(actionList));
    }

    @Test
    void testPeutPiocherOuJouerSurPlateauVide(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final GameElement movementCard =  stageModel.getBlueMovementCardsSpread().getElement(0,0);
        final GameElement movementCard2 =  stageModel.getBlueMovementCardsSpread().getElement(0,1);
        final GameElement movementCard3 =  stageModel.getBlueMovementCardsSpread().getElement(0,2);
        final GameElement movementCard4 =  stageModel.getBlueMovementCardsSpread().getElement(0,3);
        final GameElement movementCard5 =  stageModel.getBlueMovementCardsSpread().getElement(0,4);

        // Remove a card from the red AI
        movementCard.removeFromStage();
        movementCard2.removeFromStage();
        movementCard3.removeFromStage();
        movementCard4.removeFromStage();
        movementCard5.removeFromStage();

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();

        // is the AI taking a card ?
        assertEquals(2,ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVideUnderThreshold(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        // Get the AI cards
        final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0);
        final MovementCard movementCard2 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1);
        final MovementCard movementCard3 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(2);
        final MovementCard movementCard4 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(3);
        final MovementCard movementCard5 = stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(4);
        System.out.println(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0));

        // Remove a card from the Blue AI
        movementCard.setOwner(MovementCard.Owner.OUT);
        movementCard2.setOwner(MovementCard.Owner.OUT);
        movementCard3.setOwner(MovementCard.Owner.OUT);
        movementCard4.setOwner(MovementCard.Owner.OUT);


        final ActionList actionList = new ActionList();

        // Put pawns on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(null, stageModel.getModel(), stageModel.getKingPawn(), 6, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 1, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 2, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 4, 4));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();

        // Give cards to the blue AI
        final MovementCard newMovementCard1 = new MovementCard(1, MovementCard.Direction.NORTH, stageModel);
        final MovementCard newMovementCard2 = new MovementCard(1, MovementCard.Direction.SOUTH, stageModel);

        newMovementCard1.setOwner(MovementCard.Owner.PLAYER_BLUE);
        newMovementCard2.setOwner(MovementCard.Owner.PLAYER_BLUE);

        // Supprimer toutes les cartes avant d'en ajouter supprime le container ?
        movementCard5.setOwner(MovementCard.Owner.PLAYER_BLUE);

        System.out.println(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0));
        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();



        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionL));
    }

    @Test
    void testPeutJouerSurJoueurOuSurVideOverThreshold(){
        // Create the AI decider
        KoRDeciderGuide aiDecider = new KoRDeciderGuide(stageModel.getModel(), null, PlayerData.PLAYER_BLUE);

        final ActionList actionList = new ActionList();
        // Put pawns on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(null, stageModel.getModel(), stageModel.getKingPawn(), 6, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 1, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 2, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 4, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(null, stageModel.getModel(), stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 5, 4));

        new ActionPlayer(stageModel.getModel(), null, actionList).start();
        // Give cards to the blue AI
        final MovementCard newMovementCard1 = new MovementCard(1, MovementCard.Direction.NORTH, stageModel);
        final MovementCard newMovementCard2 = new MovementCard(1, MovementCard.Direction.SOUTH, stageModel);
        final MovementCard newMovementCard3 = new MovementCard(1, MovementCard.Direction.EAST, stageModel);
        final MovementCard newMovementCard4 = new MovementCard(1, MovementCard.Direction.WEST, stageModel);
        newMovementCard1.setOwner(MovementCard.Owner.PLAYER_BLUE);
        newMovementCard2.setOwner(MovementCard.Owner.PLAYER_BLUE);
        newMovementCard3.setOwner(MovementCard.Owner.PLAYER_BLUE);
        newMovementCard4.setOwner(MovementCard.Owner.PLAYER_BLUE);

        // Get the action list from the AI
        ActionList actionL = aiDecider.decide();


        assertEquals(1,ActionsUtils.actionListToInt(actionL));
    }

}