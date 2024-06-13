package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.element.card.MovementCard;

public class MovementCardLook extends ElementLook {

    private Text text1;
    private Text text2;
    private int size;

    public MovementCardLook(int size, GameElement element) {
        super(element);

        this.size = size;

        render();
    }

    @Override
    public void onSelectionChange() {
        MovementCard movementCard = (MovementCard) element;
        MovementCard.Owner owner = movementCard.getOwner();

        if (movementCard.isSelected()) {
            text1.setStrokeWidth(3);
            text1.setStrokeMiterLimit(10);
            text1.setStrokeType(StrokeType.CENTERED);
            text1.setStroke(Color.DARKGRAY);

            if(owner != MovementCard.Owner.STACK && owner != MovementCard.Owner.OUT) {
                text2.setStrokeWidth(3);
                text2.setStrokeMiterLimit(10);
                text2.setStrokeType(StrokeType.CENTERED);
                text2.setStroke(Color.DARKGRAY);
            }
        } else {
            text1.setStrokeWidth(0);
            if(owner != MovementCard.Owner.STACK && owner != MovementCard.Owner.OUT) {
                text2.setStrokeWidth(0);
            }
        }
    }

    @Override
    public void onFaceChange() {}

    protected void render() {
        MovementCard movementCard = (MovementCard) element;

        if (movementCard.getOwner() == MovementCard.Owner.STACK) {
            text1 = new Text("Pioche");
            text1.setFont(new Font(size));
            text1.setFill(Color.BLACK);

            Bounds bt = text1.getBoundsInLocal();
            text1.setX(-bt.getWidth() / 2);
            text1.setY(text1.getBaselineOffset() / 2 - 4);
            addShape(text1);
        } else {
            text1 = new Text();
            text1.setFont(new Font(size));
            text1.setFill(Color.BLACK);

            text2 = new Text();
            text2.setFont(new Font(size));
            text2.setFill(Color.BLACK);

            final String direction = String.valueOf(movementCard.getDirection());
            final String step = String.valueOf(movementCard.getStep());
            String imagePath = movementCard.getDirection().getPath(movementCard.getStep());

            Image image = new Image(imagePath);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(84);
            imageView.setFitHeight(100);
            if (movementCard.isInverted()) {

                imageView.setRotate(180);
            } else {
                text1.setText(direction);
                text2.setText(step);
            }

            Bounds bt1 = text1.getBoundsInLocal();
            text1.setX(-bt1.getWidth() / 2);
            text1.setY(text1.getBaselineOffset() / 2 - 25);

            Bounds bt2 = text2.getBoundsInLocal();
            text2.setX(-bt2.getWidth() / 2);
            text2.setY(text2.getBaselineOffset() / 2 + 20);

            Bounds bt3 = imageView.getBoundsInLocal();
            imageView.setX(-bt3.getWidth() / 2);
            imageView.setY(imageView.getBaselineOffset() - 150);

            addNode(imageView);
        }
    }
}
