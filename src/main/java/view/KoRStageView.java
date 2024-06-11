package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import javafx.scene.paint.Color;
import model.KoRStageModel;
import view.container.BlackPawnPotLook;
import view.container.KoRBoardLook;
import view.container.PawnPotLook;
import view.container.RedPawnPotLook;
import view.element.PawnLook;

public class KoRStageView extends GameStageView {

    public KoRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        KoRStageModel model = (KoRStageModel) gameStageModel;

        addLook(new KoRBoardLook(200, model.getBoard()));
        addLook(new PawnPotLook(80, 80, model.getBlackPot()));
        addLook(new PawnPotLook(80, 80, model.getRedPot()));

        for (int i = 0; i < 4; i++) {
            addLook(new PawnLook(25, model.getBlackPawns()[i]));
            addLook(new PawnLook(25, model.getRedPawns()[i]));
        }

        addLook(new TextLook(24, Color.BLACK, model.getPlayerName()));

        /* Example to show how to set a global container to layout all looks in the root pane
           Must also uncomment lines in HoleStageFactory and HoleStageModel
        ContainerLook mainLook = new ContainerLook(model.getRootContainer(), -1);
        mainLook.setPadding(10);
        mainLook.setVerticalAlignment(ContainerLook.ALIGN_MIDDLE);
        mainLook.setHorizontalAlignment(ContainerLook.ALIGN_CENTER);
        addLook(mainLook);

         */
    }
}
