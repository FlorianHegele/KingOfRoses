package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.element.Pawn;
import model.element.card.HeroCard;

public class HeroCardLook extends ElementLook {

    private int size;
    private Text text;

    public HeroCardLook(int size, GameElement element) {
        super(element);
        this.size = size;
        render();
    }

    @Override
    public void onSelectionChange() {
        HeroCard heroCard = (HeroCard) element;
        if (heroCard.isSelected()) {
            text.setStrokeWidth(3);
            text.setStrokeMiterLimit(10);
            text.setStrokeType(StrokeType.CENTERED);
            text.setStroke(Color.DARKGRAY);
        } else {
            text.setStrokeWidth(0);
        }
    }

    @Override
    public void onFaceChange() {}

    protected void render() {
        text = new Text("Hero");
        text.setFont(new Font(size));
        text.setFill(Color.BLACK);

        Bounds bt = text.getBoundsInLocal();
        text.setX(-bt.getWidth()/2);
        // since text are always above the baseline, relocate just using the part above baseline
        text.setY(text.getBaselineOffset()/2-4);
        addShape(text);
    }
}
