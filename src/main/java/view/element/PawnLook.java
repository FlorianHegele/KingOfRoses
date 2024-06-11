package view.element;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.element.Pawn;

public class PawnLook extends ElementLook {
    private Circle circle;
    private final int radius;

    public PawnLook(int radius, GameElement element) {
        super(element);

        this.radius = radius;
        render();
    }

    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn) getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(3);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.DARKGRAY);
        } else {
            circle.setStrokeWidth(0);
        }
    }

    @Override
    public void onFaceChange() {}

    protected void render() {
        Pawn pawn = (Pawn) element;
        circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(pawn.getStatus().getColor());
        addShape(circle);
    }
}
