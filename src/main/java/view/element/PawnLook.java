package view.element;

import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

//        Text text = new Text(String.valueOf(pawn.getId()));
//        text.setFont(new Font(20));
//        text.setFill(Color.BLACK);
//
//        Bounds bt1 = text.getBoundsInLocal();
//        text.setX(-bt1.getWidth()+30);
//        // since numbers are always above the baseline, relocate just using the part above baseline
//        text.setY(text.getBaselineOffset()+30);
//
//
//        addShape(text);

        Logger.debug( pawn.getStatus().name() + " / " + pawn.getContainer().getName());
    }
}
