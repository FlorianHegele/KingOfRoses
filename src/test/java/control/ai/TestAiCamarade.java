
import boardifier.control.StageFactory;
import boardifier.model.Coord2D;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.RemoveFromContainerAction;
import boardifier.view.View;
import control.ConsoleController;
import control.GameConfigurationController;
import control.KoRController;
import control.ai.KoRDeciderCamarade;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.card.MovementCardSpread;
import model.data.AIData;
import model.data.PlayerData;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import utils.ActionsUtils;
import utils.ContainerElements;
import utils.Strings;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestAiCamarade{

    private KoRStageModel stageModel;
    private GameConfigurationModel gameconfigurationModel;

    @BeforeEach
    void createStageModel() throws GameException {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model,2,1,true);
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // Load game elements
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        // Change AI of the first player to CAMARADE for the test needs
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, AIData.RANDOM, PlayerData.PLAYER_RED, AIData.CAMARADE));

        control.setFirstStageName("kor");

        control.startGame();

        stageModel = (KoRStageModel) model.getGameStage();
    }

    @Test
    void testPlateauVide(){

        // Create the AI decider
        KoRDeciderCamarade aiDecider = new KoRDeciderCamarade(stageModel.getModel(), null, PlayerData.PLAYER_RED);

        // Get the action list from the AI
        ActionList actionList = aiDecider.decide();

        // Playing a movement card involves the following :
        // Discarding a card, moving the king, putting a new pawn

        System.out.println(actionList.toString());

        // is the AI playing a movement card ?
        assertEquals(1,ActionsUtils.actionListToInt(actionList));

    }
}