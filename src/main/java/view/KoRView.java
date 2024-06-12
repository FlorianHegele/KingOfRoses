package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class KoRView extends View {

    private RadioButton guide2;
    private RadioButton hate2;
    private RadioButton camarade2;
    private RadioButton random2;
    private RadioButton guide1;
    private RadioButton hate1;
    private RadioButton camarade1;
    private RadioButton random1;
    private RadioButton menuAIVsAI;
    private RadioButton menuHumanVsAI;
    private RadioButton menuHumanVsHuman;
    private MenuItem menuConfig;
    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    private Button bValider;
    private Button bRetour;

    public KoRView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    @Override
    protected void createMenuBar(){
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Jeu");
        menuStart = new MenuItem("Nouvelle partie");
        menuIntro = new MenuItem("Retour au menu principal");
        menuQuit = new MenuItem("Quitter");
        menuConfig = new MenuItem("Configuration");

        menu1.getItems().add(menuStart);
        menu1.getItems().add(menuIntro);
        menu1.getItems().add(menuConfig);
        menu1.getItems().add(menuQuit);

        menuBar.getMenus().addAll(menu1);

    }

    public void createConfigMenu() {


        GridPane gridPane = new GridPane();

        ToggleGroup groupPlayers = new ToggleGroup();
        menuHumanVsHuman = new RadioButton("Humain vs Humain");
        menuHumanVsAI = new RadioButton("Humain vs IA");
        menuAIVsAI = new RadioButton("IA vs IA");

        menuHumanVsHuman.setToggleGroup(groupPlayers);
        menuHumanVsAI.setToggleGroup(groupPlayers);
        menuAIVsAI.setToggleGroup(groupPlayers);

        ToggleGroup groupAI1 = new ToggleGroup();
        random1 = new RadioButton("Random");
        camarade1 = new RadioButton("Camarade");
        hate1 = new RadioButton("Hate");
        guide1 = new RadioButton("Guide");

        random1.setToggleGroup(groupAI1);
        camarade1.setToggleGroup(groupAI1);
        hate1.setToggleGroup(groupAI1);
        guide1.setToggleGroup(groupAI1);

        ToggleGroup groupAI2 = new ToggleGroup();
        random2 = new RadioButton("Random");
        camarade2 = new RadioButton("Camarade");
        hate2 = new RadioButton("Hate");
        guide2 = new RadioButton("Guide");

        random2.setToggleGroup(groupAI2);
        camarade2.setToggleGroup(groupAI2);
        hate2.setToggleGroup(groupAI2);
        guide2.setToggleGroup(groupAI2);

        bRetour = new Button("Retour");
        bValider = new Button("Valider");

        // Ajout des éléments au GridPane
        gridPane.add(menuHumanVsHuman, 0, 0);
        gridPane.add(menuHumanVsAI, 1, 0);
        gridPane.add(menuAIVsAI, 2, 0);

        gridPane.add(random1, 0, 1);
        gridPane.add(camarade1, 1, 1);
        gridPane.add(hate1, 2, 1);
        gridPane.add(guide1, 3, 1);

        gridPane.add(random2, 0, 2);
        gridPane.add(camarade2, 1, 2);
        gridPane.add(hate2, 2, 2);
        gridPane.add(guide2, 3, 2);

        gridPane.add(bValider, 1, 3);
        gridPane.add(bRetour,2,3);

        // Configuring GridPane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 100, 10, 10));

        rootPane.getChildren().add(gridPane);


        stage.sizeToScene();

        rootPane.getChildren().remove(0);
        // set the clipping area with the boundaries of root pane.
        Rectangle r = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
        rootPane.setClip(r);


    }


    public RadioButton getGuide2() {
        return guide2;
    }

    public RadioButton getHate2() {
        return hate2;
    }

    public RadioButton getCamarade2() {
        return camarade2;
    }

    public RadioButton getRandom2() {
        return random2;
    }

    public RadioButton getGuide1() {
        return guide1;
    }

    public RadioButton getHate1() {
        return hate1;
    }

    public RadioButton getCamarade1() {
        return camarade1;
    }

    public RadioButton getRandom1() {
        return random1;
    }

    public RadioButton getMenuAIVsAI() {
        return menuAIVsAI;
    }

    public RadioButton getMenuHumanVsAI() {
        return menuHumanVsAI;
    }

    public RadioButton getMenuHumanVsHuman() {
        return menuHumanVsHuman;
    }

    public MenuItem getMenuConfig(){
        return menuConfig;
    }

    public MenuItem getMenuStart() {
        return menuStart;
    }

    public MenuItem getMenuIntro() {
        return menuIntro;
    }

    public MenuItem getMenuQuit() {
        return menuQuit;
    }
}
