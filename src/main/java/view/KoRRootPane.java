package view;

import boardifier.view.RootPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.FileUtils;

public class KoRRootPane extends RootPane {

    public KoRRootPane() {
        super();
    }

    @Override
    public void createDefaultGroup() {
        ImageView imageView = new ImageView();
        Image image = new Image(FileUtils.getOuputStreamFromResources("assets/king-of-roses-background.jpeg"));
        imageView.setImage(image);
        imageView.setFitWidth(925);
        imageView.setFitHeight(937);

        Font customFont = Font.loadFont(FileUtils.getOuputStreamFromResources("fonts/The Centurion .ttf"), 100);
        Text text = new Text("The King Of Roses");
        text.setFont(customFont);
        text.setFill(Color.LIGHTGREY);
        text.setStroke(Color.GREY);
        text.setX(86);
        text.setY(805);

        Rectangle border = new Rectangle();
        border.setWidth(text.getBoundsInLocal().getWidth() + 30);
        border.setHeight(text.getBoundsInLocal().getHeight());
        border.setFill(Color.rgb(0, 0, 0, 0.5));
        border.setStroke(Color.LIGHTYELLOW);
        border.setStrokeWidth(2);
        border.setArcWidth(100);
        border.setArcHeight(100);
        border.setX(70);
        border.setY(695);

        group.getChildren().clear();
        group.getChildren().addAll(imageView, border, text);
    }
}
