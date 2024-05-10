import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.view.View;
import control.KoRController;

import boardifier.control.StageFactory;
import boardifier.model.Model;

public class KoRConsole {

    public static void main(String[] args) {


        int mode = 0;
        int loggerMode = 0;
        if (args.length >= 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode < 0) || (mode > 2)) mode = 0;
            } catch (NumberFormatException e) {
                mode = 0;
            }
        }

        if (args.length == 2) {
            try {
                loggerMode = Integer.parseInt(args[1]);
                if ((loggerMode < 0) || (loggerMode > 1)) loggerMode = 0;
            } catch (NumberFormatException e) {
                loggerMode = 0;
            }
        }

        if(loggerMode == 0) {
            Logger.setLevel(Logger.LOGGER_NONE);
            Logger.setVerbosity(Logger.VERBOSE_NONE);
        } else if(loggerMode == 1) {
            Logger.setLevel(Logger.LOGGER_TRACE);
            Logger.setVerbosity(Logger.VERBOSE_HIGH);
        }


        Model model = new Model();
        if (mode == 0) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
        } else if (mode == 1) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
        } else if (mode == 2) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
        }

        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        View korView = new View(model);
        KoRController control = new KoRController(model, korView);
        control.setFirstStageName("kor");
        try {
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }

}