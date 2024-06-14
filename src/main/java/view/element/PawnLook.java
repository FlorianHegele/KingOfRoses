package view.element;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.element.Pawn;
import utils.FileUtils;

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
        circle.setFill(Color.TRANSPARENT);
        addShape(circle);
        if (pawn.getStatus() == Pawn.Status.KING_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("king-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(70);
            imageView.setX(-35);
            imageView.setY(-35);
            addNode(imageView);
        }
        else if (pawn.getStatus() == Pawn.Status.BLUE_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("blue-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(53);
            imageView.setFitHeight(63);
            imageView.setX(-25);
            imageView.setY(-30);
            addNode(imageView);
        }
        else if (pawn.getStatus() == Pawn.Status.RED_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("red-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(54);
            imageView.setFitHeight(60);
            imageView.setX(-27);
            imageView.setY(-28);
            addNode(imageView);
        }
    }
}
