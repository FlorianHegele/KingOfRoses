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

/**
 * Initializes and manages the boardifier and its components.
 */
public class Boardifiers {

    private final Stage stage;
    private final GameConfigurationModel gameConfigurationModel;
    private final Model model;

    private KoRController control;

    /**
     * Constructs a Boardifiers instance with the specified stage, model, and game configuration.
     *
     * @param stage the primary stage for the game.
     * @param model the game model.
     * @param gameConfigurationModel the game configuration model.
     */
    public Boardifiers(Stage stage, Model model, GameConfigurationModel gameConfigurationModel) {
        this.stage = stage;
        this.model = model;
        this.gameConfigurationModel = gameConfigurationModel;
    }

    /**
     * Initializes the game by setting up the sound, registering the stage, creating the view, and controllers.
     */
    public void init() {
        Sound.playMusic("main.wav",1000);
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

        if(gameConfigurationModel.isRenderGame()) {
            // set the stage title
            stage.setTitle("The King of Roses");
            // show the scene
            stage.show();
        }
    }

    /**
     * Returns the game model.
     *
     * @return the game model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Returns the current stage model.
     *
     * @return the KoRStageModel of the current game stage.
     */
    public KoRStageModel getStageModel() {
        return (KoRStageModel) model.getGameStage();
    }

    /**
     * Returns the game controller.
     *
     * @return the KoRController.
     */
    public KoRController getController() {
        return control;
    }
}
