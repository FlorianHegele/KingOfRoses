package model;

import boardifier.model.*;
import model.container.KoRBoard;
import model.container.KoRPawnPot;
import model.element.Pawn;

public class KoRStageFactory extends StageElementsFactory {
    private KoRStageModel stageModel;

    public KoRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (KoRStageModel) gameStageModel;
    }

    @Override
    public void setup() {

        // create the board
        KoRBoard board = new KoRBoard(10, 50, stageModel);
        stageModel.setBoard(board);
        //create the pots
        KoRPawnPot blackPot = new KoRPawnPot(400,10, stageModel);
        stageModel.setBlackPot(blackPot);
        KoRPawnPot redPot = new KoRPawnPot(500,10, stageModel);
        stageModel.setRedPot(redPot);

        // create the pawns
        Pawn[] blackPawns = new Pawn[4];
        for(int i=0;i<4;i++) {
            blackPawns[i] = new Pawn(i + 1, Pawn.PAWN_BLACK, stageModel);
        }
        stageModel.setBlackPawns(blackPawns);
        Pawn[] redPawns = new Pawn[4];
        for(int i=0;i<4;i++) {
            redPawns[i] = new Pawn(i + 1, Pawn.PAWN_RED, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        // assign pawns to their pot
        for (int i=0;i<4;i++) {
            blackPot.addElement(blackPawns[i], i,0);
            redPot.addElement(redPawns[i], i,0);
        }
        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(10,20);
        stageModel.setPlayerName(text);

        /*
        // create the main container
        ContainerElement mainContainer = new ContainerElement("rootcontainer",0,0,2,3, stageModel);
        mainContainer.setCellSpan(0,1,2,1);
        mainContainer.setCellSpan(0,2,2,1);
        stageModel.setRootContainer(mainContainer);
        // assign element to root container cells
        mainContainer.addElement(text,0,0);
        mainContainer.addElement(board, 1,0);
        mainContainer.addElement(blackPot,0,1);
        mainContainer.addElement(redPot,0,2);

         */
    }
}
