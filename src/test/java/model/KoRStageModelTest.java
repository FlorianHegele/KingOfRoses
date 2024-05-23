package model;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Boardifiers;

import static org.junit.jupiter.api.Assertions.*;

class KoRStageModelTest {

    private KoRStageModel stageModel;
    private Model model;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create the model
        model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, 3, 2,
                0, false, true);

        // Init and start the Game
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
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        final ActionList actionList = new ActionList();
        for(Pawn pawn : stageModel.getBluePawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));

        new ActionPlayer(model, null, actionList).start();

        assertEquals(stageModel.getRedPot(), stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetGeneralEmptyPawnPot() {
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

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> stageModel.getPlayedPawn(-1, -1));
        assertNull(stageModel.getPlayedPawn(0, 0)); // i = 0
        assertNull(stageModel.getPlayedPawn(4, 4)); // i = 1

        // PLACE
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, pawn, stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        assertEquals(pawn, stageModel.getPlayedPawn(4, 4)); // i = 2
    }

    @Test
    void testTotalPlayerPoint() {
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

        // representation of part of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX

        assertEquals(6, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 3, 3));
        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_BLUE, 3, 3));
    }

    @Test
    void testGetTotalPawnZone() {
        assertEquals(0, stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));

        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0],
                stageModel.getBoard().getName(), 0, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1],
                stageModel.getBoard().getName(), 2, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0],
                stageModel.getBoard().getName(), 1, 1)).start();

        assertEquals(2, stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));
        assertEquals(1, stageModel.getTotalPawnZone(PlayerData.PLAYER_BLUE));

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 2, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[4], stageModel.getBoard().getName(), 3, 2));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[5], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        // representation of part of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX

        assertEquals(1, stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));
    }

    @Test
    void testGetZoneAverage() {
        assertEquals(0, stageModel.getZoneAverage(PlayerData.PLAYER_RED));

        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0],
                stageModel.getBoard().getName(), 0, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1],
                stageModel.getBoard().getName(), 2, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2],
                stageModel.getBoard().getName(), 3, 0)).start();
        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0],
                stageModel.getBoard().getName(), 1, 1)).start();

        assertEquals(1.5, stageModel.getZoneAverage(PlayerData.PLAYER_RED));
        assertEquals(1, stageModel.getZoneAverage(PlayerData.PLAYER_BLUE));

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 3, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[1], stageModel.getBoard().getName(), 4, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[2], stageModel.getBoard().getName(), 2, 3));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[3], stageModel.getBoard().getName(), 3, 4));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[4], stageModel.getBoard().getName(), 3, 2));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[5], stageModel.getBoard().getName(), 4, 4));
        new ActionPlayer(model, null, actionList).start();

        // representation of part of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX

        assertEquals(6, stageModel.getZoneAverage(PlayerData.PLAYER_RED));
    }

    @Test
    void testComputePartyResultTie() {
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

    @Test
    void playerCanPlayPawnVer() {
        // if they all have their pawns
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));

        final ActionList actionList = new ActionList();
        for(int i=0; i<25; i++) {
            actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[i], stageModel.getBoard().getName(), 0, 0));
            actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[i], stageModel.getBoard().getName(), 0, 0));
        }
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[25], stageModel.getBoard().getName(), 0, 0));
        new ActionPlayer(model, null, actionList).start();

        // if the red player has no pawn left in his pot but the blue player has at least 1 pawn left in his pot
        // returns true because the red player can draw from the blue pawn pot
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));

        new ActionPlayer(model, null, ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[25], stageModel.getBoard().getName(), 0, 0)).start();

        // if both players have no pawn left in their pot
        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));
    }

    @Test
    void testPlayerCanPlayHero() {
        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generateMoveWithinContainer(model, stageModel.getKingPawn(), 6, 8));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 8, 8));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 6, 5));
        new ActionPlayer(model, null, actionList).start();

        // representation of part of the board, O is the king and X is the blue pawns
        // X 0
        //
        //   X

        // With the random seed 3, and the king's pawn in 6, 8 + a blue pawn in 8,8 and 6,5.
        // Only the red player can use a hero card.
        // The blue player cannot play a hero card on his own pawn.

        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));

        // Deletes all the red player's hero cards
        for(int i=0; i<4; i++)
            new ActionPlayer(model, null,
                    ActionFactory.generateRemoveFromContainer(model, stageModel.getRedHeroCardStack().getElement(0, 0))
            ).start();

        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
    }

    @Test
    void testPlayerCanPlayGetCardFromTheStack() {
        final ActionList actionList = new ActionList();
        for(int i=0; i<4; i++)
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, stageModel.getMovementCards(MovementCard.Owner.PLAYER_RED).get(i)));
        new ActionPlayer(model, null, actionList).start();

        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
    }

    @Test
    void testGameIsStuck() {
        assertFalse(stageModel.gameIsStuck());

        final ActionList actionList = new ActionList();
        actionList.addAll(ActionFactory.generateMoveWithinContainer(model, stageModel.getKingPawn(), 6, 8));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 8, 8));
        actionList.addAll(ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[1], stageModel.getBoard().getName(), 6, 5));
        new ActionPlayer(model, null, actionList).start();

        // representation of part of the board, O is the king and X is the blue pawns
        // X 0
        //
        //   X

        // With the random seed 3, and the king's pawn in 6, 8 + a blue pawn in 8,8 and 6,5.
        // Only the red player can use a hero card.
        // The blue player cannot play a hero card on his own pawn.

        assertFalse(stageModel.gameIsStuck());

        for(int i=0; i<4; i++)
            // Deletes all the red player's hero cards
            new ActionPlayer(model, null,
                    ActionFactory.generateRemoveFromContainer(model, stageModel.getRedHeroCardStack().getElement(0, 0))
            ).start();

        assertTrue(stageModel.gameIsStuck());
    }

    // TODO : every Callbacks

}












