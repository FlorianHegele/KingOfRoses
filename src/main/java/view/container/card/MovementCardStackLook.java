package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * The MovementCardStackLook class represents the visual representation of a movement card stack using a look.
 * It provides specific configurations for rendering the movement card stack.
 */
public class MovementCardStackLook extends GridLook {

    private Rectangle rectangle;

    /**
     * Constructs a new MovementCardStackLook with the specified container element.
     *
     * @param containerElement The container element associated with this movement card stack look.
     */
    public MovementCardStackLook(int height, int width, ContainerElement containerElement) {
        super(height, width, containerElement, -1, 1, Color.BLACK);
    }

    @Override
    protected void render() {
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);

        rectangle = new Rectangle(colWidth, rowHeight, Color.WHITE);
        rectangle.setStrokeWidth(3);
        rectangle.setStrokeMiterLimit(10);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setX(borderWidth);
        rectangle.setY(borderWidth);

        addShape(rectangle);
    }

}
