package utils;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import model.data.ElementType;

/**
 * The {@code ContainerElements} class provides utility methods for working with elements in a container.
 */
public class ContainerElements {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ContainerElements() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Retrieves the position of a game element within a container element.
     *
     * @param gameElement      the game element whose position is to be retrieved.
     * @param containerElement the container element containing the game element.
     * @return the position of the game element as a {@link Coord2D} object, or null if the element is not found.
     */
    public static Coord2D getElementPosition(GameElement gameElement, ContainerElement containerElement) {
        final int[] cell = containerElement.getElementCell(gameElement);
        return (cell == null) ? null : new Coord2D(cell[1], cell[0]);
    }

    /**
     * Retrieves the position of an empty slot within a container element.
     *
     * @param containerElement the container element to search for an empty slot.
     * @return the position of an empty slot as a {@link Coord2D} object, or null if no empty slot is found.
     */
    public static Coord2D getEmptyPosition(ContainerElement containerElement) {
        for (int row = 0; row < containerElement.getNbRows(); row++) {
            for (int col = 0; col < containerElement.getNbCols(); col++) {
                if (containerElement.isEmptyAt(row, col))
                    return new Coord2D(col, row);
            }
        }
        return null;
    }

    /**
     * Counts the total number of elements stored in a container element.
     *
     * @param containerElement the container element whose elements are to be counted.
     * @return the total number of elements in the container.
     */
    public static int countElements(ContainerElement containerElement) {
        int count = 0;
        for (int row = 0; row < containerElement.getNbRows(); row++) {
            for (int col = 0; col < containerElement.getNbCols(); col++) {
                count += containerElement.getElements(row, col).size();
            }
        }
        return count;
    }

    public static boolean isOutside(ContainerElement containerElement, int row, int col) {
        Logger.trace("isOutside(" + row + ", " + col + ")");
        return ((row >= 0) && (row < containerElement.getNbRows()) && (col >= 0) && (col < containerElement.getNbCols()));
    }

    public static <T extends GameElement> T getSelectedElement(GameStageModel gameStageModel, ElementType elementType) {
        for (GameElement element : gameStageModel.getSelected()) {
            if (elementType.isElement(element)) return (T) element;
        }
        throw new IllegalCallerException("Element not found");
    }
}
