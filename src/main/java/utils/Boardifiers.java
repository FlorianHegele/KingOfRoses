package utils;

import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.KoRController;
import javafx.stage.Stage;
import model.GameConfigurationModel;
import model.KoRStageModel;
import control.Sound;
import view.KoRRootPane;
import view.KoRView;

public class Boardifiers {

    private final Stage stage;
    private final GameConfigurationModel gameConfigurationModel;
    private final Model model;

    private KoRController control;

    public Boardifiers(Stage stage, Model model, GameConfigurationModel gameConfigurationModel) {
        this.stage = stage;
        this.model = model;
        this.gameConfigurationModel = gameConfigurationModel;
    }

    public void initGame() {
        Sound.playMusic("src/main/resources/main.mp3",1000);
        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");

        // create the root pane, using the subclass HoleRootPane
        KoRRootPane rootPane = new KoRRootPane();
        // create the global view.
        KoRView view = new KoRView(model, stage, rootPane);
        // create the controllers.

        control = new KoRController(model, view, gameConfigurationModel);
        // set the name of the first stage to create when the game is started
        control.setFirstStageName("kor");
        // set the stage title
        stage.setTitle("The King of Roses");
        // show the scene
        stage.show();

        dirtyImplementation();
    }

    // TODO : REIMPLEMENT
    private void dirtyImplementation() {
        gameConfigurationModel.addPlayers("player1", "player2");
    }

    public Model getModel() {
        return model;
    }

    public KoRStageModel getStageModel() {
        return (KoRStageModel) model.getGameStage();
    }

    public KoRController getController() {
        return control;
    }
}
