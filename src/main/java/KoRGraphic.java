import boardifier.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameConfigurationModel;
import model.data.AIData;
import model.data.PlayerData;
import utils.Boardifiers;

import java.util.Map;

import static model.GameConfigurationModel.*;

public class KoRGraphic extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // create the model
        Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, 1,
                2, 3, DEFAULT_PLAYER_INTERACTION, DEFAULT_RENDER_GAME);

        final AIData blueAI = AIData.RANDOM;
        final AIData redAI = AIData.RANDOM;
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, blueAI, PlayerData.PLAYER_RED, redAI));


        // Init Game
        final Boardifiers boardifiers = new Boardifiers(stage, model, gameConfigurationModel);
        boardifiers.initGame();
    }
}
