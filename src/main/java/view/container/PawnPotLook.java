package view.container;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class PawnPotLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle rectangle;

    public PawnPotLook(int height, int width, ContainerElement element) {
        super(height / 4, width, element, -1, 1, Color.BLACK);

    }

    protected void render() {
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);

        rectangle = new Rectangle(colWidth, rowHeight, Color.WHITE);
        rectangle.setStrokeWidth(3);
        rectangle.setStrokeMiterLimit(10);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setX(borderWidth);
        rectangle.setY((double)rowHeight + borderWidth);

        addShape(rectangle);
    }

}
