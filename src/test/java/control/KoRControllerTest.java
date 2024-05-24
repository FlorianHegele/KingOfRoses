package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.data.AIData;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.MovementCard;
import org.junit.jupiter.api.Test;
import utils.Boardifiers;
import utils.ContainerElements;
import utils.Strings;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

    private void setupRandomVsAI(AIData aiData) throws GameException {
        // Create the model
        model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, RANDOM_SEED, 1,
                0, false, false);
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_RED, aiData));
        // Init and start the Game
        final Boardifiers boardifiers = new Boardifiers(model, gameConfigurationModel);
        boardifiers.initGame();
        boardifiers.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
        controller = boardifiers.getController();
    }


    /*
     * AI
     */

    @Test
    void AICanPlay() throws GameException {
        writeInput("D1");

        setupRandomVsAI(AIData.RANDOM);
        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN));
    }


    /*
     * MOVEMENT CARD
     */

    @Test
    void playerCantPlayWrongCardMovementVersion() throws GameException {
        writeInput("D", "D0", "D-1", "D6");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
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

        setupBoard(false, RANDOM_SEED);
        new ActionPlayer(model, null, ActionFactory.generateRemoveFromStage(model, stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0))).start();
        controller.stageLoop();

        assertEquals(1, model.getIdPlayer());
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
    }

    @Test
    void playerCantDrawMovementCard() throws GameException {
        writeInput("P");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
    }

    @Test
    void playerCantPlacePawnOutsideMovementCardVersion() throws GameException {
        writeInput("D5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generateMoveWithinContainer(model, stageModel.getKingPawn(), 0, 0);
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerNeedToUseHeroCard() throws GameException {
        writeInput("D5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 4, 2);
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
    }

    @Test
    void playerEmptyMovementCardVersion() throws GameException {
        writeInput("D1");

        setupBoard(false, 1);

        final ActionList actionList = new ActionList();
        for(int i=0; i<5; i++)
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(i)));
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    /*
     * HERO CARD
     */

    @Test
    void playerCantPlayWrongCardMovementHeroVersion() throws GameException {
        writeInput("H", "H0", "H-1", "H6");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerCantPlayEmptyHeroCardCell() throws GameException {
        writeInput("H5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generatePutInContainer(model, stageModel.getRedPawns()[0], stageModel.getBoard().getName(), 4, 2);
        for(int i=0; i<4; i++)
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, stageModel.getBlueHeroCards()[i]));

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
    void playerCantUseHeroCardOnHisOwnPawn() throws GameException {
        writeInput("H5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generatePutInContainer(model, stageModel.getBluePawns()[0], stageModel.getBoard().getName(), 4, 2);
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(1, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
        assertEquals(5, ContainerElements.countElements(stageModel.getBlueMovementCardsSpread()));
        assertEquals(4, ContainerElements.countElements(stageModel.getBlueHeroCardStack()));
    }

    @Test
    void playerCantPlacePawnOutsideHeroCardVersion() throws GameException {
        writeInput("H5");

        setupBoard(false, 1);

        final ActionList actionList = ActionFactory.generateMoveWithinContainer(model, stageModel.getKingPawn(), 0, 0);
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerEmptyMovementCardHeroVersion() throws GameException {
        writeInput("H1");

        setupBoard(false, 1);

        final ActionList actionList = new ActionList();
        for(int i=0; i<5; i++)
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(i)));
        new ActionPlayer(model, null, actionList).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
    }

    @Test
    void playerCantPlayEmptyMovementCardCellHeroVersion() throws GameException {
        writeInput("H1");

        setupBoard(false, 1);

        new ActionPlayer(model, null, ActionFactory.generateRemoveFromStage(model,
                stageModel.getBlueMovementCardsSpread().getElement(0, 0))).start();

        controller.stageLoop();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }

    @Test
    void playerCantPlayHeroCardOnEmptyCell() throws GameException {
        writeInput("H1");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
        assertEquals(0, stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN));
    }


    /*
     * BASIC
     */

    @Test
    void testStopInput() throws GameException {
        writeInput();

        setupBoardRandom();

        assertTrue(controller.sendStop);
        assertEquals(PlayerData.NONE.getId(), model.getIdWinner());
    }

    @Test
    void emptyInput() throws GameException {
        writeInput("");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
    }

    @Test
    void wrongInput() throws GameException {
        writeInput("P1", "B", "C");

        setupBoardRandom();

        assertEquals(0, model.getIdPlayer());
    }

    @Test
    void switchPlayerOnToTwo() throws GameException {
        writeInput("D1");

        setupBoardRandom();

        assertEquals(1, model.getIdPlayer());
    }

    @Test
    void switchPlayerTwoToOn() throws GameException {
        writeInput("D1", "D2");

        setupBoard(1);

        assertEquals(0, model.getIdPlayer());
    }


    /*
     * Call Back
     */


    private void writeInput(String... inputs) {
        final String input = Strings.join(inputs) + "\nstop";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

}
