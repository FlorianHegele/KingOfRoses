package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Pawn extends GameElement {

    private int number;
    private int color;
    public static int PAWN_BLACK = 0;
    public static int PAWN_RED = 1;

    public Pawn(int number, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // TODO : register a new type of element for the pawns
        // TODO : initialize attributes
    }

    public int getNumber() {
        return number;
    }
    public int getColor() {
        return color;
    }
}