package model;

import boardifier.model.Model;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KoRStageModelTest {

    private KoRStageModel stageModel;
    private Model model;
    private KoRBoard board;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);

        GameConfigurationModel.RANDOM.setSeed(3);
        stageModel.getDefaultElementFactory().setup();

        board = stageModel.getBoard();
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
        assertEquals(stageModel.getKingPawn(), board.getElement(4, 4));
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

        final PawnPot bluePot = new PawnPot(0, 0, stageModel);

        stageModel.setBluePot(bluePot);

        assertEquals(stageModel.getRedPot(), stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetGeneralEmptyPawnPot() {
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        final PawnPot bluePot = new PawnPot(0, 0, stageModel);
        final PawnPot redPot = new PawnPot(0, 0, stageModel);

        stageModel.setBluePot(bluePot);
        stageModel.setRedPot(redPot);

        assertNull(stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetPlayedPawn() {
        final Pawn pawn = stageModel.getBluePawns()[0];

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> stageModel.getPlayedPawn(-1, -1));
        assertNull(stageModel.getPlayedPawn(0, 0)); // i = 0
        assertNull(stageModel.getPlayedPawn(4, 4)); // i = 1


        board.addElement(pawn, 4, 4);
        // PLACE

        assertEquals(pawn, stageModel.getPlayedPawn(4, 4)); // i = 2
    }

    @Test
    void testTotalPlayerPoint() {
        final PlayerData playerData = PlayerData.PLAYER_BLUE;
        final boolean[][] defaultCell = board.getReachableCells().clone();

        assertEquals(0, stageModel.getTotalPlayerPointSimple(playerData));

        board.addElement(stageModel.getBluePawns()[0], 0, 0);
        board.addElement(stageModel.getBluePawns()[1], 1, 0);
        board.addElement(stageModel.getBluePawns()[2], 0, 1);
        board.addElement(stageModel.getBluePawns()[3], 4, 4);
        board.addElement(stageModel.getBluePawns()[4], 5, 5);
        board.addElement(stageModel.getRedPawns()[0], 4, 5);
        board.addElement(stageModel.getRedPawns()[1], 3, 5);
        board.addElement(stageModel.getRedPawns()[2], 6, 6);

        assertEquals(11, stageModel.getTotalPlayerPointSimple(playerData));

        // Check reset of reachable cells
        assertArrayEquals(defaultCell, board.getReachableCells());

        assertEquals(5, stageModel.getTotalPlayerPointSimple(playerData.getNextPlayerData()));
    }

    @Test
    void testGetTotalPawnOnBoard() {
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        board.addElement(stageModel.getBluePawns()[0], 0, 0);
        board.addElement(stageModel.getBluePawns()[1], 1, 0);
        board.addElement(stageModel.getBluePawns()[2], 1, 1);
        board.addElement(stageModel.getRedPawns()[0], 3, 4);
        board.addElement(stageModel.getRedPawns()[1], 4, 4);

        assertEquals(3, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(2, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    @Test
    void testGetNeighbor() {
        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 0, 0)).size());
        board.resetReachableCells(true);

        board.addElement(stageModel.getRedPawns()[0], 1, 0);
        board.addElement(stageModel.getBluePawns()[0], 0, 0);

        assertEquals(1, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 0, 0)).size());
        board.resetReachableCells(true);
        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 0, 0)).size());
        board.resetReachableCells(true);
        assertEquals(1, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 1, 0)).size());
        board.resetReachableCells(true);

        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getRedPawns()[2], 2, 3);
        board.addElement(stageModel.getRedPawns()[3], 3, 4);
        board.addElement(stageModel.getRedPawns()[4], 3, 2);
        board.addElement(stageModel.getRedPawns()[5], 4, 4);

        assertEquals(4, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.RED_PAWN, 3, 3)).size());
        board.resetReachableCells(true);
        assertEquals(0, stageModel.getNeighbors(new KoRStageModel.PawnNode(Pawn.Status.BLUE_PAWN, 3, 3)).size());
    }

    @Test
    void testGetZonePawn() {
        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 0, 0));

        board.addElement(stageModel.getRedPawns()[0], 0, 0);
        board.addElement(stageModel.getRedPawns()[1], 2, 0);
        board.addElement(stageModel.getBluePawns()[0], 1, 1);

        assertEquals(0, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 1, 1));
        assertEquals(1, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 0, 0));
        // Can detect neighbours even if the current position contains no pawns
        assertEquals(2, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_RED, 1, 0));
        assertEquals(1, stageModel.getPlayerZonePawnSimple(PlayerData.PLAYER_BLUE, 1, 0));


        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getRedPawns()[2], 2, 3);
        board.addElement(stageModel.getRedPawns()[3], 3, 4);
        board.addElement(stageModel.getRedPawns()[4], 3, 2);
        board.addElement(stageModel.getRedPawns()[5], 4, 4);

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

        board.addElement(stageModel.getRedPawns()[0], 0, 0);
        board.addElement(stageModel.getRedPawns()[1], 2, 0);
        board.addElement(stageModel.getBluePawns()[0], 1, 1);

        assertEquals(2, stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));
        assertEquals(1, stageModel.getTotalPawnZone(PlayerData.PLAYER_BLUE));
    }

    @Test
    void testGetTotalPawnZone2() {
        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getRedPawns()[2], 2, 3);
        board.addElement(stageModel.getRedPawns()[3], 3, 4);
        board.addElement(stageModel.getRedPawns()[4], 3, 2);
        board.addElement(stageModel.getRedPawns()[5], 4, 4);

        // representation of part of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX
        assertEquals(1, stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));
    }

    @Test
    void testGetZoneAverage() {
        assertEquals(0, stageModel.getZoneAverage(PlayerData.PLAYER_RED));

        board.addElement(stageModel.getRedPawns()[0], 0, 0);
        board.addElement(stageModel.getRedPawns()[1], 2, 0);
        board.addElement(stageModel.getRedPawns()[2], 3, 0);
        board.addElement(stageModel.getBluePawns()[0], 1, 1);

        assertEquals(1.5, stageModel.getZoneAverage(PlayerData.PLAYER_RED));
        assertEquals(1, stageModel.getZoneAverage(PlayerData.PLAYER_BLUE));
    }

    @Test
    void testGetZoneAverage2() {
        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getRedPawns()[2], 2, 3);
        board.addElement(stageModel.getRedPawns()[3], 3, 4);
        board.addElement(stageModel.getRedPawns()[4], 3, 2);
        board.addElement(stageModel.getRedPawns()[5], 4, 4);

        // representation of part of the board, O is the 3,3 pawn
        //   X
        //  XOX
        //   XX

        assertEquals(6, stageModel.getZoneAverage(PlayerData.PLAYER_RED));
    }

    @Test
    void testComputePartyResultTie() {
        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getBluePawns()[0], 4, 4);
        board.addElement(stageModel.getBluePawns()[1], 5, 4);

        stageModel.computePartyResult();
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    @Test
    void testComputePartyResultSquarePoint() {
        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getBluePawns()[0], 4, 4);

        stageModel.computePartyResult();
        assertEquals(PlayerData.PLAYER_RED.getId(), model.getIdWinner());
    }

    @Test
    void testComputePartyResultTotalPawn() {
        board.addElement(stageModel.getRedPawns()[0], 3, 3);
        board.addElement(stageModel.getRedPawns()[1], 4, 3);
        board.addElement(stageModel.getBluePawns()[0], 4, 4);
        board.addElement(stageModel.getBluePawns()[1], 5, 5);
        board.addElement(stageModel.getBluePawns()[2], 6, 6);
        board.addElement(stageModel.getBluePawns()[3], 7, 7);

        stageModel.computePartyResult();
        assertEquals(PlayerData.PLAYER_BLUE.getId(), model.getIdWinner());
    }

    @Test
    void playerCanPlayPawnVer() {
        // if they all have their pawns
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));

        for(int i=0; i<25; i++) {
            stageModel.getRedPot().removeElement(stageModel.getRedPawns()[i]);
            stageModel.getBluePot().removeElement(stageModel.getBluePawns()[i]);
        }
        stageModel.getRedPot().removeElement(stageModel.getRedPawns()[25]);

        // if the red player has no pawn left in his pot but the blue player has at least 1 pawn left in his pot
        // returns true because the red player can draw from the blue pawn pot
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));

        stageModel.getBluePot().removeElement(stageModel.getBluePawns()[25]);


        // if both players have no pawn left in their pot
        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_BLUE));
    }

    @Test
    void testPlayerCanPlayHero() {
        board.moveElement(stageModel.getKingPawn(), 6, 8);

        board.addElement(stageModel.getBluePawns()[0], 8, 8);
        stageModel.getBluePot().removeElement(stageModel.getBluePawns()[0]);

        board.addElement(stageModel.getBluePawns()[1], 6, 5);
        stageModel.getBluePot().removeElement(stageModel.getBluePawns()[1]);

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
            stageModel.getRedHeroCardStack().getElement(0, 0).removeFromStage();

        assertFalse(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
    }

    @Test
    void testPlayerCanPlayGetCardFromTheStack() {
        for(int i=0; i<4; i++)
            stageModel.getMovementCards(MovementCard.Owner.PLAYER_RED).get(i).removeFromStage();

        assertTrue(stageModel.playerCanPlay(PlayerData.PLAYER_RED));
    }

    @Test
    void testGameIsStuck() {
        assertFalse(stageModel.gameIsStuck());

        board.moveElement(stageModel.getKingPawn(), 6, 8);

        board.addElement(stageModel.getBluePawns()[0], 8, 8);
        stageModel.getBluePot().removeElement(stageModel.getBluePawns()[0]);

        board.addElement(stageModel.getBluePawns()[1], 6, 5);
        stageModel.getBluePot().removeElement(stageModel.getBluePawns()[1]);

        // representation of part of the board, O is the king and X is the blue pawns
        // X 0
        //
        //   X

        // With the random seed 3, and the king's pawn in 6, 8 + a blue pawn in 8,8 and 6,5.
        // Only the red player can use a hero card.
        // The blue player cannot play a hero card on his own pawn.

        assertFalse(stageModel.gameIsStuck());

        for(int i=0; i<4; i++)
            stageModel.getRedHeroCards()[i].removeFromStage();

        assertTrue(stageModel.gameIsStuck());
    }

}












