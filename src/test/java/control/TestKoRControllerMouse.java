package control;

import boardifier.model.Model;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.KoRBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestKoRControllerMouse {

    private KoRStageModel stageModel;
    private Model model;
    private KoRBoard board;
    private KoRControllerMouse controllerMouse;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);
        model.setGameStage(stageModel);
        controllerMouse = new KoRControllerMouse(model, null, null);

        GameConfigurationModel.RANDOM.setSeed(3);
        stageModel.getDefaultElementFactory().setup();

        board = stageModel.getBoard();
    }

    @Test
    void testEmptySelection() {

    }
}
