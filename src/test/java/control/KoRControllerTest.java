package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.Test;
import utils.Boardifiers;
import utils.ContainerElements;
import utils.Strings;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static model.GameConfigurationModel.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KoRControllerTest {

    private KoRStageModel stageModel;
    private KoRController controller;
    private Model model;

    // Need to play this function after changing the System.in if we want to test input
    private void setupBoard(boolean startLoop, long seed) throws GameException {
        // Create the model
        model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, seed, 0,
                0, startLoop, false);

        // Init and start the Game
        final Boardifiers boardifiers = new Boardifiers(model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
        controller = boardifiers.getController();
    }

    private void setupBoard(long random) throws GameException {
        setupBoard(true, random);
    }

    private void setupBoardRandom() throws GameException {
        setupBoard(true, RANDOM_SEED);
    }

    @Test
    void testStopInput() throws GameException {
        writeInput();

        setupBoardRandom();

        assertTrue(controller.sendStop);
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    @Test
    void playerCantPlayEmptyMovementCardCell() throws GameException {
        writeInput("D1");

        setupBoard(false, 1);

        new ActionPlayer(model, null, ActionFactory.generateRemoveFromStage(model,
                stageModel.getBlueMovementCardsSpread().getElement(0, 0))).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerCanPlayMovementCard() throws GameException {
        writeInput("D1");

        setupBoard(true, 1);

        controller.stageLoop();

        assertEquals(1, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerCanDrawMovementCard() throws GameException {
        writeInput("P");

        setupBoard(false, 1);
        new ActionPlayer(model, null, ActionFactory.generateRemoveFromContainer(model, stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0))).start();
        controller.stageLoop();

        assertEquals(1, model.getIdPlayer());
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
    }

    @Test
    void playerCantDrawMovementCard() throws GameException {
        writeInput("P");

        setupBoard(1);

        assertEquals(0, model.getIdPlayer());
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
    }

    @Test
    void playerCantPlayEmptyHeroCardCell() throws GameException {
        writeInput("H5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 4, 2);
        for(int i=0; i<4; i++)
            actionList.addAll(ActionFactory.generateRemoveFromContainer(model, stageModel.getBlueHeroCards()[i]));

        new ActionPlayer(model, null, actionList).start();
        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }

    @Test
    void playerCanPlayHeroCard() throws GameException {
        writeInput("H5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 4, 2);

        new ActionPlayer(model, null, actionList).start();
        controller.stageLoop();

        assertEquals(1, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerCantPlacePawnOutOfTheBoard() throws GameException {
        writeInput("D2", "D2");

        setupBoard(1);

        assertEquals(1, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
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

}
