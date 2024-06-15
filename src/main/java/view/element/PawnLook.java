package view.element;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.element.Pawn;
import utils.FileUtils;

public class PawnLook extends ElementLook {

    public PawnLook(GameElement element) {
        super(element, ((Pawn)element).getStatus() == Pawn.Status.KING_PAWN ? 10 : -1);

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
