package model;

import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * KoR main board represent the element where pawns are put when played
 * Thus, a simple ContainerElement with 9 rows and 9 column is needed.
 * Nevertheless, in order to "simplify" the work for the controller part,
 * this class also contains method to determine all the valid cells to put a
 * pawn with a given value.
 */
public class KoRBoard extends ContainerElement {
    // TODO : Modify this method to create the board with the right size and name (9x9 grid)
    public KoRBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 3x3 grid, named "holeboard", and in x,y in space
        super("holeboard", x, y, 3 , 3, gameStageModel);
    }

    // TODO : check if this method works with the new board size
    public void setValidCells(int number) {
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }
    public List<Point> computeValidCells(int number) {
        List<Point> lst = new ArrayList<>();
         /*
        TODO:
            - compute the list of cells that are valid to play taking the pawn value (i.e. number) into account.
            each Point in this list consists in couple x,y, where x is a column and y a row in the board.
         */
        return lst;
    }
}
