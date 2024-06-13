package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.data.PlayerData;
import view.KoRView;

/**
 * A basic action controller that only manages menu actions
 * Action events are mostly generated when there are user interactions with widgets like
 * buttons, checkboxes, menus, ...
 */
public class KoRControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

    // to avoid lots of casts, create an attribute that matches the instance type.
    private final KoRView koRView;
    private final GameConfigurationModel gameConfigurationModel;

    public KoRControllerAction(Model model, View view, Controller control,GameConfigurationModel gameConfigurationModel) {
        super(model, view, control);
        // take the view parameter ot define a local view attribute with the real instance type, i.e. BasicView.
        koRView = (KoRView) view;
        this.gameConfigurationModel = gameConfigurationModel;

        // set handlers dedicated to menu items
        setMenuHandlers();

        // If needed, set the general handler for widgets that may be included within the scene.
        // In this case, the current gamestage view must be retrieved and casted to the right type
        // in order to have an access to the widgets, and finally use setOnAction(this).
        // For example, assuming the current gamestage view is an instance of MyGameStageView, which
        // creates a Button myButton :
        // ((MyGameStageView)view.getCurrentGameStageView()).getMyButton().setOnAction(this).

    }

    private void setMenuHandlers() {

        // set event handler on the MenuStart item
        koRView.getMenuStart().setOnAction(e -> {
            try {
                control.startGame();
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
        // set event handler on the MenuIntro item
        koRView.getMenuIntro().setOnAction(e -> {
            control.stopGame();
            koRView.resetView();
        });

        // set event handler on the Configuration menu item
        koRView.getMenuConfig().setOnAction(e -> {
            control.stopGame();
            koRView.resetView();
            koRView.createConfigMenu();

            // set event handler on the radio buttons
            koRView.getMenuHumanVsHuman().setOnAction(f -> {gameConfigurationModel.setPlayerMode(0);activateGroup(0);});
            koRView.getMenuHumanVsAI().setOnAction(f -> {gameConfigurationModel.setPlayerMode(1);activateGroup(1);});
            koRView.getMenuAIVsAI().setOnAction(f -> {gameConfigurationModel.setPlayerMode(2);activateGroup(2);});

            switch (gameConfigurationModel.getPlayerMode()){
                case 0:
                    koRView.getMenuHumanVsHuman().setSelected(true);
                    activateGroup(0);
                    break;
                case 1:
                    koRView.getMenuHumanVsAI().setSelected(true);
                    activateGroup(1);
                    break;
                case 2:
                    koRView.getMenuAIVsAI().setSelected(true);
                    activateGroup(2);
                    break;
            }

            switch (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_RED)){
                case RANDOM -> koRView.getRandom1().setSelected(true);
                case CAMARADE -> koRView.getCamarade1().setSelected(true);
                case HATE_CARDS -> koRView.getHate1().setSelected(true);
                case GUIDE -> koRView.getGuide1().setSelected(true);
            }

            switch (gameConfigurationModel.getPlayerDataAIDataMap().get(PlayerData.PLAYER_BLUE)){
                case RANDOM -> koRView.getRandom2().setSelected(true);
                case CAMARADE -> koRView.getCamarade2().setSelected(true);
                case HATE_CARDS -> koRView.getHate2().setSelected(true);
                case GUIDE -> koRView.getGuide2().setSelected(true);
            }
        });



        // set event handler on the MenuQuit item
        koRView.getMenuQuit().setOnAction(e -> {
            System.exit(0);
        });

    }

    public void activateGroup(int playerMode){
        switch (playerMode){
            case 0:
                koRView.getRandom1().setDisable(true);
                koRView.getCamarade1().setDisable(true);
                koRView.getHate1().setDisable(true);
                koRView.getGuide1().setDisable(true);
                koRView.getRandom2().setDisable(true);
                koRView.getCamarade2().setDisable(true);
                koRView.getHate2().setDisable(true);
                koRView.getGuide2().setDisable(true);
                break;
            case 1:
                koRView.getRandom1().setDisable(true);
                koRView.getCamarade1().setDisable(true);
                koRView.getHate1().setDisable(true);
                koRView.getGuide1().setDisable(true);
                koRView.getRandom2().setDisable(false);
                koRView.getCamarade2().setDisable(false);
                koRView.getHate2().setDisable(false);
                koRView.getGuide2().setDisable(false);
                break;
            case 2:
                koRView.getRandom1().setDisable(false);
                koRView.getCamarade1().setDisable(false);
                koRView.getHate1().setDisable(false);
                koRView.getGuide1().setDisable(false);
                koRView.getRandom2().setDisable(false);
                koRView.getCamarade2().setDisable(false);
                koRView.getHate2().setDisable(false);
                koRView.getGuide2().setDisable(false);
                break;
        }
    }

    /**
     * The general handler for action events.
     * this handler should be used if the code to process a particular action event is too long
     * to fit in an arrow function (like with menu items above). In this case, this handler must be
     * associated to a widget w, by calling w.setOnAction(this) (see constructor).
     *
     * @param event An action event generated by a widget of the scene.
     */
    public void handle(ActionEvent event) {

        if (!model.isCaptureActionEvent()) {
        }
    }
}

