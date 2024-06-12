package view.container;

import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import utils.FileUtils;

public class KoRBoardLook extends ClassicBoardLook {

    public KoRBoardLook(int size, ContainerElement element) {
        // NB: To have more liberty in the design, GridLook does not compute the cell size from the dimension of the element parameter.
        // If we create the 3x3 board by adding a border of 10 pixels, with cells occupying all the available surface,
        // then, cells have a size of (size-20)/3
        super(size / 3, element, -1, Color.BEIGE, Color.DARKGRAY, 0, Color.BLACK, 5, Color.BLACK, false);
    }

    @Override
    protected void render() {
        super.render();
        Image cardBackground = new Image(FileUtils.getOuputStreamFromResources("rdr-plateau-vide.png"));
        Rectangle rectangle = new Rectangle(getWidth()+5, getHeight()+5);
        rectangle.setFill(new ImagePattern(cardBackground));
        addShape(rectangle);
    }
}
