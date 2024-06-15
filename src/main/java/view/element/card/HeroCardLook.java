package view.element.card;

import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.element.card.HeroCard;
import utils.FileUtils;

public class HeroCardLook extends ElementLook {

    private ImageView imageView;

    public HeroCardLook(GameElement element) {
        super(element);
        render();
    }

    @Override
    public void onSelectionChange() {
        Logger.info("HeroCardLook onSelectionChange " + element.isSelected());

        if(element.isSelected()) {
            imageView.setBlendMode(BlendMode.DIFFERENCE);
        } else {
            imageView.setBlendMode(BlendMode.SRC_OVER);
        }

    }

    protected void render() {
        final HeroCard heroCard = (HeroCard) element;

        Logger.info("render");

        final Image image;
        imageView = new ImageView();
        if(heroCard.getStatus() == HeroCard.Status.RED_CARD) {
           image = new Image(FileUtils.getOuputStreamFromResources("hero-card-red.png"));
           imageView.setRotate(180);
        } else {
            image = new Image(FileUtils.getOuputStreamFromResources("hero-card-blue.png"));
        }
        imageView.setImage(image);
        imageView.setFitWidth(93);
        imageView.setFitHeight(115);

        Bounds bt1 = imageView.getBoundsInLocal();
        imageView.setX(-bt1.getWidth() + 49);
        // since text are always above the baseline, relocate just using the part above baseline
        imageView.setY(imageView.getBaselineOffset()-163);

        addNode(imageView);
    }
}
