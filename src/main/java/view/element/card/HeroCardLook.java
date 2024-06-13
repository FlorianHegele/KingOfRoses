package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.element.card.HeroCard;
import utils.FileUtils;

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
        HeroCard heroCard = (HeroCard) element;

        text = new Text("Hero");
        text.setFont(new Font(size));
        text.setFill(Color.BLACK);

        if (heroCard.getStatus() == HeroCard.Status.RED_CARD) {
            Image image = new Image(FileUtils.getOuputStreamFromResources("hero-card-red.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(93);
            imageView.setFitHeight(115);

            imageView.setRotate(180);

            Bounds bt1 = imageView.getBoundsInLocal();
            imageView.setX(-bt1.getWidth() + 49);
            // since text are always above the baseline, relocate just using the part above baseline
            imageView.setY(text.getBaselineOffset() - 78);

            addNode(imageView);
        } else {
            Image image = new Image(FileUtils.getOuputStreamFromResources("hero-card-blue.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(110);


            Bounds bt = text.getBoundsInLocal();
            text.setX(-bt.getWidth() / 2);
            // since text are always above the baseline, relocate just using the part above baseline
            text.setY(text.getBaselineOffset() / 2 - 4);

            Bounds bt1 = imageView.getBoundsInLocal();
            imageView.setX(-bt1.getWidth() + 45);
            // since text are always above the baseline, relocate just using the part above baseline
            imageView.setY(text.getBaselineOffset() - 75);

            addShape(text);
            addNode(imageView);
        }

    }
}
