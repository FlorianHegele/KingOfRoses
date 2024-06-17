package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import javafx.scene.layout.Pane;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.KoRBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.KoRView;

class TestKoRControllerMouse {

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
        model.setGameStage(stageModel);

        stageModel.getDefaultElementFactory().setup();

        board = stageModel.getBoard();
    }

    @Test
    void testEmptySelection() {
        KoRView view = Mockito.mock(KoRView.class);
        KoRController controller = Mockito.mock(KoRController.class);

        Pane pane = new Pane();
        Mockito.when(view.getRootPane()).thenReturn(pane);

        KoRControllerMouse mouse = new KoRControllerMouse(model, view, controller);
    }
}
