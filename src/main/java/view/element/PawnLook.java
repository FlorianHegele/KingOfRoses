package view.element;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.element.Pawn;
import utils.FileUtils;

public class PawnLook extends ElementLook {

    public PawnLook(GameElement element) {
        super(element);

        render();
    }

    @Override
    public void onFaceChange() {
        clearGroup();
        render();
    }

    protected void render() {
        Pawn pawn = (Pawn) element;
        final Image image;
        final ImageView imageView = new ImageView();
        if (pawn.getStatus() == Pawn.Status.KING_PAWN){
            image = new Image(FileUtils.getOuputStreamFromResources("assets/king-pawn.png"));
            imageView.setFitWidth(56);
            imageView.setFitHeight(56);
            imageView.setX(-27);
            imageView.setY(-30);
        }
        else if (pawn.getStatus() == Pawn.Status.BLUE_PAWN){
            image = new Image(FileUtils.getOuputStreamFromResources("assets/blue-pawn.png"));
            imageView.setImage(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(70);
            imageView.setX(-28.5);
            imageView.setY(-36);
        }
        else if (pawn.getStatus() == Pawn.Status.RED_PAWN){
            image = new Image(FileUtils.getOuputStreamFromResources("assets/red-pawn.png"));
            imageView.setImage(image);
            imageView.setFitWidth(61);
            imageView.setFitHeight(69);
            imageView.setX(-29);
            imageView.setY(-33);
        } else {
            throw new IllegalStateException("State not implemented yet (" + pawn.getStatus() + ")");
        }

        imageView.setImage(image);
        addNode(imageView);
    }
}
