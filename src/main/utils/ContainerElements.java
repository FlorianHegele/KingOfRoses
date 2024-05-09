package utils;

import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;

public class ContainerElements {

    private ContainerElements() {
        throw new IllegalStateException("Utility class");
    }

    public static Coord2D getElementPosition(GameElement gameElement, ContainerElement containerElement) {
        final int[] cell = containerElement.getElementCell(gameElement);
        return (cell == null) ? null : new Coord2D(cell[1], cell[0]);
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
