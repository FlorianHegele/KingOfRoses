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
        if (pawn.getStatus() == Pawn.Status.KING_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("assets/king-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            imageView.setX(-29);
            imageView.setY(-32);
            addNode(imageView);
        }
        else if (pawn.getStatus() == Pawn.Status.BLUE_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("assets/blue-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(68);
            imageView.setFitHeight(78);
            imageView.setX(-32);
            imageView.setY(-40);
            addNode(imageView);
        }
        else if (pawn.getStatus() == Pawn.Status.RED_PAWN){
            Image image = new Image(FileUtils.getOuputStreamFromResources("assets/red-pawn.png"));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(66);
            imageView.setFitHeight(76);
            imageView.setX(-33);
            imageView.setY(-38);
            addNode(imageView);
        }
    }
}
