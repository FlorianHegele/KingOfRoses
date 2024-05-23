package model;

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
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Boardifiers;

import static org.junit.jupiter.api.Assertions.*;

class KoRStageModelTest {

    private KoRStageModel stageModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, 2, 0, false, true);

        // Init Game
        final Boardifiers boardifiers = new Boardifiers(model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
    }

    @Test
    void testPawnNumber() {
        // 52 (26*2) total
        assertEquals(26, stageModel.getBluePawns().length);
        assertEquals(26, stageModel.getRedPawns().length);
    }

    @Test
    void testMovementCardNumber() {
        assertEquals(24, stageModel.getMovementCards().length);
    }

    @Test
    void testHeroCardNumber() {
        assertEquals(4, stageModel.getBlueHeroCards().length);
        assertEquals(4, stageModel.getRedHeroCards().length);
    }

    @Test
    void testKingPawnCenter() {
        assertEquals(stageModel.getKingPawn(), stageModel.getBoard().getElement(4, 4));
    }

    @Test
    void testUniqueMovementCard() {
        final MovementCard[] movementCardList = stageModel.getMovementCards();
        for(int i=0; i<movementCardList.length; i++) {
            final MovementCard iMovementCard = movementCardList[i];
            if(iMovementCard.isInverted()) iMovementCard.toggleInverted();

            for(int j=i+1; j<movementCardList.length; j++) {
                final MovementCard jMovementCard = movementCardList[j];
                if(jMovementCard.isInverted()) jMovementCard.toggleInverted();

                assertNotEquals(iMovementCard, jMovementCard);
            }
        }
    }

    @Test
    void testGetPawnPot() {
        assertEquals(stageModel.getBluePot(), stageModel.getPawnPot(PlayerData.PLAYER_BLUE));
        assertEquals(stageModel.getRedPot(), stageModel.getPawnPot(PlayerData.PLAYER_RED));
    }


    @Test
    void testGetGeneralPawnPot() {
        assertEquals(stageModel.getBluePot(), stageModel.getGeneralPot(PlayerData.PLAYER_BLUE));
    }

    @Test
    void testGetGeneralPawnPotWithItsEmptyPot() {
        final Model model = stageModel.getModel();
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        final ActionList actionList = new ActionList();
        for(Pawn pawn : stageModel.getBluePawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));

        new ActionPlayer(model, null, actionList).start();

        assertEquals(stageModel.getRedPot(), stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetGeneralEmptyPawnPot() {
        final Model model = stageModel.getModel();
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        final ActionList actionList = new ActionList();
        for(Pawn pawn : stageModel.getBluePawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));
        for(Pawn pawn : stageModel.getRedPawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));

        new ActionPlayer(model, null, actionList).start();

        assertNull(stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetPlayedPawn() {
        final Pawn pawn = stageModel.getBluePawns()[0];
        final Model model = stageModel.getModel();

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> stageModel.getPlayedPawn(-1, -1));
        assertNull(stageModel.getPlayedPawn(0, 0)); // i = 0
        assertNull(stageModel.getPlayedPawn(4, 4)); // i = 1

        // PLACE
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, pawn, stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        assertEquals(pawn, stageModel.getPlayedPawn(4, 4)); // i = 2
    }

    // TODO : gameIsStuck, playerCanPlay, every Callbacks

    @Test
    void testTotalPlayerPoint() {
        final Model model = stageModel.getModel();
        final PlayerData playerData = PlayerData.PLAYER_BLUE;
        final boolean[][] defaultCell = stageModel.getBoard().getReachableCells().clone();

        assertEquals(0, stageModel.getTotalPlayerPointSimple(playerData));

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 1, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 0, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[3], stageModel.getBoard().getName(), 4, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[4], stageModel.getBoard().getName(), 5, 5));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 4, 5));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 3, 5));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 6, 6));
        new ActionPlayer(model, null, actionList).start();

        assertEquals(11, stageModel.getTotalPlayerPointSimple(playerData));

        // Check reset of reachable cells
        assertArrayEquals(defaultCell, stageModel.getBoard().getReachableCells());

        assertEquals(5, stageModel.getTotalPlayerPointSimple(playerData.getNextPlayerData()));
    }

    @Test
    void testGetTotalPawnOnBoard() {
        final Model model = stageModel.getModel();
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 0, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 1, 0));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 1, 1));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        assertEquals(3, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(2, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    @Test
    void testGetNeighbor() {
        final Model model = stageModel.getModel();

        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 0, 0)).size());
        stageModel.getBoard().resetReachableCells(true);

        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0],
                stageModel.getBoard().getName(), 1, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0],
                stageModel.getBoard().getName(), 0, 0)).start();

        assertEquals(1, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 0, 0)).size());
        stageModel.getBoard().resetReachableCells(true);
        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 0, 0)).size());
        stageModel.getBoard().resetReachableCells(true);
        assertEquals(1, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 1, 0)).size());
        stageModel.getBoard().resetReachableCells(true);


        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 2, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[4], stageModel.getBoard().getName(), 3, 2));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[5], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        assertEquals(4, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 3, 3)).size());
        stageModel.getBoard().resetReachableCells(true);
        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 3, 3)).size());
    }

    @Test
    void testGetZonePawn() {
        final Model model = stageModel.getModel();

        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 0, 0));

        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0],
                stageModel.getBoard().getName(), 0, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1],
                stageModel.getBoard().getName(), 2, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0],
                stageModel.getBoard().getName(), 1, 1)).start();

        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 1, 1));
        assertEquals(1, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 0, 0));
        // Can detect neighbours even if the current position contains no pawns
        assertEquals(2, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 1, 0));
        assertEquals(1, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_BLUE, 1, 0));

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 2, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[4], stageModel.getBoard().getName(), 3, 2));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[5], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        // Representation of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX

        assertEquals(6, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 3, 3));
        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_BLUE, 3, 3));
    }

    @Test
    void testComputePartyResultTie() {
        final Model model = stageModel.getModel();

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 4, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 5, 4));
        new ActionPlayer(model, null, actionList).start();

        stageModel.computePartyResult(false);
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    @Test
    void testComputePartyResultSquarePoint() {
        final Model model = stageModel.getModel();

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        stageModel.computePartyResult(false);
        assertEquals(PlayerData.PLAYER_RED.getId(), model.getIdWinner());
    }

    @Test
    void testComputePartyResultTotalPawn() {
        final Model model = stageModel.getModel();

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 4, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 5, 5));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[2], stageModel.getBoard().getName(), 6, 6));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[4], stageModel.getBoard().getName(), 7, 7));
        new ActionPlayer(model, null, actionList).start();

        stageModel.computePartyResult(false);
        assertEquals(PlayerData.PLAYER_BLUE.getId(), model.getIdWinner());
    }

}












