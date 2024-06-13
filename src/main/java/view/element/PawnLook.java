package view.element;

import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    public void onFaceChange() {
        Pawn pawn = (Pawn) element;
        circle.setFill(pawn.getStatus().getColor());
    }

    protected void render() {
        Pawn pawn = (Pawn) element;
        circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(pawn.getStatus().getColor());
        addShape(circle);
    }
}
