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
    private void setupBoard() throws GameException {
        // Create the model
        model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, RANDOM_SEED, 0,
                0, true, false);

        // Init and start the Game
        boardifiers = new Boardifiers(model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
        controller = boardifiers.getController();
    }

    @Test
    void testStopInput() throws GameException {
        writeInput();

        setupBoard();

        assertTrue(controller.sendStop);
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    @Test
    void testWrongInput() throws GameException {
        writeInput("A", "B", "D");

        setupBoard();

        assertTrue(nothingChange());
    }

    private void writeInput(String... inputs) {
        final String input = Strings.join(inputs) + "\nstop";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private boolean nothingChange(int redPawnOnBoard, int bluePawnOnBoard, int movementCardInStack) {
        return stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN) == redPawnOnBoard
                && stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN) == bluePawnOnBoard
                && stageModel.getMovementCards(MovementCard.Owner.STACK).size() == movementCardInStack;
    }

    private boolean nothingChange() {
        return nothingChange(0, 0, 14);
    }
}
