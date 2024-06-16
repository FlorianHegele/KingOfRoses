package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class MovementCardSpreadLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle[] cells;

    public MovementCardSpreadLook(int height, int width, ContainerElement containerElement) {
        super(height, width / 5, containerElement, -1, 1, Color.TRANSPARENT);

    }

    protected void render() {
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);
        cells = new Rectangle[5];
        // create the rectangles.
        for (int i = 0; i < 5; i++) {
            cells[i] = new Rectangle(colWidth, rowHeight, Color.TRANSPARENT);
            cells[i].setStrokeWidth(3);
            cells[i].setStrokeMiterLimit(10);
            cells[i].setStrokeType(StrokeType.CENTERED);
            cells[i].setStroke(Color.valueOf("0x333333"));
            cells[i].setX(i * colWidth + (double)borderWidth);
            cells[i].setY(borderWidth);
            // cells[i].setY((double)i * rowHeight + borderWidth);
            addShape(cells[i]);
        }
    }

}
