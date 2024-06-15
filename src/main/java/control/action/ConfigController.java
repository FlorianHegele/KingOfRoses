package control.action;

import boardifier.model.GameException;
import control.KoRControllerAction;
import control.Sound;
import model.GameConfigurationModel;
import model.data.AIData;
import model.data.PlayerData;
import model.data.WindowType;
import view.KoRView;
import view.window.ConfigView;

public class ConfigController extends WindowController {

    private final GameConfigurationModel gameConfigurationModel;

    public ConfigController(KoRControllerAction actionController, KoRView koRView) {
        super(actionController, koRView, WindowType.CONFIG);
        this.gameConfigurationModel = actionController.getGameConfigurationModel();
    }

    @Override
    public void setHandler() {
        final ConfigView configView = (ConfigView) windowView;

        // set event handler on the radio buttons to select gamemode
        configView.getMenuHumanVsHuman().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(0);
            gameConfigurationModel.getPlayerDataAIDataMap().clear();
            activateGroup(0);
            autoSelectAIButtons();
        });

        configView.getMenuHumanVsAI().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(1);
            activateGroup(1);
            // Put Random AI to the second player by default if it hasn't been selected before
            gameConfigurationModel.getPlayerDataAIDataMap().remove(PlayerData.PLAYER_BLUE);
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_RED)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_RED, AIData.RANDOM);
            }
            setP2AiButtonsHandlers();
            autoSelectAIButtons();
        });

        configView.getMenuAIVsAI().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(2);
            activateGroup(2);
            // Put Random AI for both players if it hasn't been selected before
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_RED)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_RED, AIData.RANDOM);
            }
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_BLUE)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_BLUE, AIData.RANDOM);
            }
            setP1AiButtonsHandlers();
            setP2AiButtonsHandlers();
            autoSelectAIButtons();
        });

        // set event handler on the radio buttons to select gamemode
        configView.getMenuHumanVsHuman().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(0);
            gameConfigurationModel.getPlayerDataAIDataMap().clear();
            activateGroup(0);
            autoSelectAIButtons();
        });

        configView.getMenuHumanVsAI().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(1);
            activateGroup(1);
            // Put Random AI to the second player by default if it hasn't been selected before
            gameConfigurationModel.getPlayerDataAIDataMap().remove(PlayerData.PLAYER_BLUE);
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_RED)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_RED, AIData.RANDOM);
            }
            setP2AiButtonsHandlers();
            autoSelectAIButtons();
        });

        configView.getMenuAIVsAI().setOnAction(f -> {
            gameConfigurationModel.setPlayerMode(2);
            activateGroup(2);
            // Put Random AI for both players if it hasn't been selected before
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_RED)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_RED, AIData.RANDOM);
            }
            if(!gameConfigurationModel.getPlayerDataAIDataMap().containsKey(PlayerData.PLAYER_BLUE)) {
                gameConfigurationModel.getPlayerDataAIDataMap().put(PlayerData.PLAYER_BLUE, AIData.RANDOM);
            }
            setP1AiButtonsHandlers();
            setP2AiButtonsHandlers();
            autoSelectAIButtons();
        });

        if (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_BLUE) != null) {
            setP1AiButtonsHandlers();
        }

        if (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_RED) != null) {
            setP2AiButtonsHandlers();
        }

        switch (gameConfigurationModel.getPlayerMode()) {
            case 0:
                configView.getMenuHumanVsHuman().setSelected(true);
                activateGroup(0);
                break;
            case 1:
                configView.getMenuHumanVsAI().setSelected(true);
                activateGroup(1);
                break;
            case 2:
                configView.getMenuAIVsAI().setSelected(true);
                activateGroup(2);
                break;
        }

        autoSelectAIButtons();

        configView.getbValider().setOnAction(f->{
            try {
                Sound.playSound("sword1.wav");
                Sound.playMusic("Daydream.wav");
                gameConfigurationModel.addPlayers("Player 1 - Blue","Player 2 - Red");
                controller.startGame();
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
    }

    private void setP2AiButtonsHandlers() {
        final ConfigView configView = (ConfigView) windowView;

        configView.getRandom2().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_RED, AIData.RANDOM));
        configView.getCamarade2().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_RED, AIData.CAMARADE));
        configView.getHate2().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_RED, AIData.HATE_CARDS));
        configView.getGuide2().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_RED, AIData.GUIDE));
    }

    private void setP1AiButtonsHandlers() {
        final ConfigView configView = (ConfigView) windowView;

        configView.getRandom1().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_BLUE, AIData.RANDOM));
        configView.getCamarade1().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_BLUE, AIData.CAMARADE));
        configView.getHate1().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_BLUE, AIData.HATE_CARDS));
        configView.getGuide1().setOnAction(f -> gameConfigurationModel.getPlayerDataAIDataMap().replace(PlayerData.PLAYER_BLUE, AIData.GUIDE));
    }

    private void autoSelectAIButtons() {
        final ConfigView configView = (ConfigView) windowView;

        if (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_BLUE) != null) {
            switch (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_BLUE)) {
                case RANDOM -> configView.getRandom1().setSelected(true);
                case CAMARADE -> configView.getCamarade1().setSelected(true);
                case HATE_CARDS -> configView.getHate1().setSelected(true);
                case GUIDE -> configView.getGuide1().setSelected(true);
            }
        }
        if (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_RED) != null) {
            switch (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_RED)) {
                case RANDOM -> configView.getRandom2().setSelected(true);
                case CAMARADE -> configView.getCamarade2().setSelected(true);
                case HATE_CARDS -> configView.getHate2().setSelected(true);
                case GUIDE -> configView.getGuide2().setSelected(true);
            }
        }
    }

    private void activateGroup(int playerMode){
        final ConfigView configView = (ConfigView) windowView;

        switch (playerMode){
            case 0:
                configView.getRandom1().setDisable(true);
                configView.getCamarade1().setDisable(true);
                configView.getHate1().setDisable(true);
                configView.getGuide1().setDisable(true);
                configView.getRandom2().setDisable(true);
                configView.getCamarade2().setDisable(true);
                configView.getHate2().setDisable(true);
                configView.getGuide2().setDisable(true);
                break;
            case 1:
                configView.getRandom1().setDisable(true);
                configView.getCamarade1().setDisable(true);
                configView.getHate1().setDisable(true);
                configView.getGuide1().setDisable(true);
                configView.getRandom2().setDisable(false);
                configView.getCamarade2().setDisable(false);
                configView.getHate2().setDisable(false);
                configView.getGuide2().setDisable(false);
                break;
            case 2:
                configView.getRandom1().setDisable(false);
                configView.getCamarade1().setDisable(false);
                configView.getHate1().setDisable(false);
                configView.getGuide1().setDisable(false);
                configView.getRandom2().setDisable(false);
                configView.getCamarade2().setDisable(false);
                configView.getHate2().setDisable(false);
                configView.getGuide2().setDisable(false);
                break;
        }
    }

}
