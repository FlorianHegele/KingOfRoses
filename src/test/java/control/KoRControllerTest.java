package control;

import boardifier.model.GameException;
import boardifier.model.Model;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Boardifiers;
import utils.Strings;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static model.GameConfigurationModel.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KoRControllerTest {

    private Boardifiers boardifiers;
    private KoRStageModel stageModel;
    private KoRController controller;
    private Model model;

    // Need to play this function after changing the System.in if we want to test input
    private void setupBoard(long seed) throws GameException {
        // Create the model
        model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, seed, 0,
                0, true, false);

        // Init and start the Game
        boardifiers = new Boardifiers(model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
        controller = boardifiers.getController();
    }

    private void setupBoardRandom() throws GameException {
        setupBoard(RANDOM_SEED);
    }

    @Test
    void testStopInput() throws GameException {
        writeInput();

        setupBoardRandom();

        assertTrue(controller.sendStop);
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    // TODO : Rewrite this test
    // + Do not test SimpleActionList because
    // if we test every entry for the player,
    // it means that every functions in this
    // class have been used!
    @Test
    void playerCantPlacePawnOutOfTheBoard() throws GameException {
        writeInput("D2", "D2");

        setupBoard(1);

        assertEquals(1, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    @Test
    void playerCantPlayEmptyCard() throws GameException {
        writeInput("D2", "D5", "D2");

        setupBoard(1);

        assertEquals(0, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    @Test
    void playerCantPlacePawnOnOtherPawn() throws GameException {
        writeInput("D3", "D2", "D5", "P", "P", "D2", "D4", "D5");

        setupBoard(7);

        assertEquals(1, model.getIdPlayer());
        assertEquals(3, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(2, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    private void writeInput(String... inputs) {
        final String input = Strings.join(inputs) + "\nstop";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    /*
    private boolean nothingChange(int redPawnOnBoard, int bluePawnOnBoard, int movementCardInStack) {
        return stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN) == redPawnOnBoard
                && stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN) == bluePawnOnBoard
                && stageModel.getMovementCards(MovementCard.Owner.STACK).size() == movementCardInStack;
    }

    private boolean nothingChange() {
        return nothingChange(0, 0, 14);
    }
     */
}
