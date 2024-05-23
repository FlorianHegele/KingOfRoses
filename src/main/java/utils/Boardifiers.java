package utils;

import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.ConsoleController;
import control.GameConfigurationController;
import control.KoRController;
import model.GameConfigurationModel;
import model.KoRStageModel;

public class Boardifiers {

    private final ConsoleController consoleController;
    private final GameConfigurationModel gameConfigurationModel;
    private final Model model;

    private KoRController controller;

    public Boardifiers(Model model, GameConfigurationModel gameConfigurationModel) {
        this(model, new ConsoleController(true), gameConfigurationModel);
    }

    public Boardifiers(Model model, ConsoleController consoleController, GameConfigurationModel gameConfigurationModel) {
        this.model = model;
        this.gameConfigurationModel = gameConfigurationModel;
        this.consoleController = consoleController;

        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();
    }

    public void initGame() {
        // Load game elements
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);

        this.controller = new KoRController(model, korView, consoleController, gameConfigurationModel);
        controller.setFirstStageName("kor");
    }

    public void startGame() throws GameException {
        if (controller == null || model.isEndGame())
            throw new IllegalStateException("You must first initialise the game before launching it !");
        controller.startGame();
        if (gameConfigurationModel.isPlayerInteraction()) controller.stageLoop();
    }

    public Model getModel() {
        return model;
    }

    public KoRStageModel getStageModel() {
        return (KoRStageModel) model.getGameStage();
    }

    public KoRController getController() {
        return controller;
    }
}
