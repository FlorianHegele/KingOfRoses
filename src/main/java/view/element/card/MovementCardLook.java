package view.element.card;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.element.card.MovementCard;
import utils.FileUtils;

public class MovementCardLook extends ElementLook {

    private ImageView imageView;

    public MovementCardLook(GameElement element) {
        super(element);

        render();
    }

    @Override
    public void onSelectionChange() {
        if(element.isSelected()) {
            imageView.setBlendMode(BlendMode.GREEN);
        } else {
            imageView.setBlendMode(BlendMode.SRC_OVER);
        }
    }

    @Override
    public void onFaceChange() {
        clearGroup();
        render();
    }

    protected void render() {
        MovementCard movementCard = (MovementCard) element;

        imageView = new ImageView();
        if (movementCard.getOwner() == MovementCard.Owner.STACK) {
            Image image = new Image(FileUtils.getOuputStreamFromResources("stack.png"));
            imageView.setImage(image);
            imageView.setFitHeight(155);
            imageView.setFitWidth(160);

            Bounds bt1 = imageView.getBoundsInLocal();
            imageView.setX(-bt1.getWidth() + 81.5);
            imageView.setY(imageView.getBaselineOffset() - 225);
        } else {
            String imagePath = movementCard.getDirection().getPath(movementCard.getStep());

            Image image = new Image(imagePath);
            imageView.setImage(image);
            imageView.setFitWidth(84);
            imageView.setFitHeight(100);

            if (movementCard.isInverted()) {
                imageView.setRotate(180);
            }

            Bounds bt3 = imageView.getBoundsInLocal();
            imageView.setX(-bt3.getWidth() / 2);
            imageView.setY(imageView.getBaselineOffset() - 150);
        }
        addNode(imageView);
    }
}
