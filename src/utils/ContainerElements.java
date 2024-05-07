package utils;

import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import model.element.Pawn;

public class ContainerElements {

    private ContainerElements() {
        throw new IllegalStateException("Utility class");
    }

    public static Coord2D getElementPosition(GameElement gameElement, ContainerElement containerElement) {
        for(int x=0; x<containerElement.getNbRows(); x++) {
            for(int y=0; y<containerElement.getNbCols(); y++) {
                if(containerElement.isEmptyAt(x, y)) continue;
                if(containerElement.getElement(x ,y).getType() == gameElement.getType()) return new Coord2D(x, y);
            }
        }
        return null;
    }

    public static Coord2D getPawnPosition(Pawn.Status pawnStatus, ContainerElement containerElement) {
        for(int row=0; row<containerElement.getNbRows(); row++) {
            for(int col=0; col<containerElement.getNbCols(); col++) {
                if(containerElement.isEmptyAt(row, col)) continue;
                if(containerElement.getElement(row ,col) instanceof Pawn pawnElement) {
                    if(pawnElement.getStatus() == pawnStatus)
                        return new Coord2D(col, row);
                }
            }
        }
        return null;
    }

    public static Coord2D geEmptyPosition(ContainerElement containerElement) {
        for(int row=0; row<containerElement.getNbRows(); row++) {
            for(int col=0; col<containerElement.getNbCols(); col++) {
                if(containerElement.isEmptyAt(row, col))
                    return new Coord2D(row, col);
            }
        }
        return null;
    }

    public static int countElements(ContainerElement containerElement) {
        int count = 0;
        for(int row=0; row<containerElement.getNbRows(); row++) {
            for(int col=0; col<containerElement.getNbCols(); col++) {
                count += containerElement.getElements(row, col).size();
            }
        }
        return count;
    }

}
