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

import static org.junit.jupiter.api.Assertions.*;

class KoRStageModelTest {

    private static KoRStageModel stageModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model);
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // Load game elements
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        control.setFirstStageName("kor");

        control.startGame();

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
        final Model model = stageModel.getModel();
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        assertEquals(stageModel.getBluePot(), stageModel.getGeneralPot(playerData));

        final ActionList actionList = new ActionList();
        for(Pawn pawn : stageModel.getBluePawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));

        new ActionPlayer(model, null, actionList).start();

        assertNotEquals(stageModel.getBluePot(), stageModel.getGeneralPot(playerData));
    }

    @Test
    void testGetGeneralPawnPotWithItsEmptyPot() {
        final Model model = stageModel.getModel();
        final PlayerData playerData = PlayerData.PLAYER_BLUE;

        final ActionList actionList = new ActionList();
        for(Pawn pawn : stageModel.getBluePawns())
            actionList.addAll(ActionFactory.generateRemoveFromStage(model, pawn));

        new ActionPlayer(model, null, actionList).start();

        assertNotEquals(stageModel.getRedPot(), stageModel.getGeneralPot(playerData));
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
}












