package view.element.card;

import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.element.card.HeroCard;
import model.element.card.MovementCard;

public class MovementCardLook extends ElementLook {

    private Rectangle rectangle;
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
            rectangle.setStrokeWidth(3);
            rectangle.setStrokeMiterLimit(10);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.setStroke(Color.DARKGRAY);

            text1.setStrokeWidth(3);
            text1.setStrokeMiterLimit(10);
            text1.setStrokeType(StrokeType.CENTERED);
            text1.setStroke(Color.DARKGRAY);

            if(owner.isPlayer()) {
                text2.setStrokeWidth(3);
                text2.setStrokeMiterLimit(10);
                text2.setStrokeType(StrokeType.CENTERED);
                text2.setStroke(Color.DARKGRAY);
            }
        } else {
            rectangle.setStrokeWidth(0);
            text1.setStrokeWidth(0);
            if(owner.isPlayer()) {
                text2.setStrokeWidth(0);
            }
        }
    }

    protected void render() {
        MovementCard movementCard = (MovementCard) element;

        rectangle = new Rectangle(size*2, size*2);
        rectangle.setFill(Color.ORANGE);

        addShape(rectangle);

        if(movementCard.getOwner() == MovementCard.Owner.STACK) {
            text1 = new Text("Pioche");
            text1.setFont(new Font(size));
            text1.setFill(Color.BLACK);

            Bounds bt = text1.getBoundsInLocal();
            text1.setX(-bt.getWidth()/2);
            // since numbers are always above the baseline, relocate just using the part above baseline
            text1.setY(text1.getBaselineOffset()/2-4);
            addShape(text1);
        } else {
            text1 = new Text();
            text1.setFont(new Font(size));
            text1.setFill(Color.BLACK);

            text2 = new Text();
            text2.setFont(new Font(size));
            text2.setFill(Color.BLACK);

            final String direction = movementCard.getDirection().getSymbol();
            final String step = String.valueOf(movementCard.getStepRepresentation());
            if(movementCard.isInverted()) {
                text1.setText(step);
                text2.setText(direction);
            } else {
                text1.setText(direction);
                text2.setText(step);
            }


            Bounds bt1 = text1.getBoundsInLocal();
            text1.setX(-bt1.getWidth()/2);
            // since numbers are always above the baseline, relocate just using the part above baseline
            text1.setY(text1.getBaselineOffset()/2-25);

            Bounds bt2 = text2.getBoundsInLocal();
            text2.setX(-bt2.getWidth()/2);
            // since numbers are always above the baseline, relocate just using the part above baseline
            text2.setY(text2.getBaselineOffset()/2+20);


            addShape(text1);
            addShape(text2);
        }
    }
}
