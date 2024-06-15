package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import control.Sound;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    private MenuItem menuMusique;
    private MenuItem menuSFX;
    private MenuItem sLow;
    private MenuItem sMedium;
    private MenuItem sHigh;
    private MenuItem sMax;
    private MenuItem mLow;
    private MenuItem mMedium;
    private MenuItem mHigh;
    private MenuItem mMax;
    private Button bValider;
    private Menu menu3;
    private Menu menu4;

    public KoRView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    @Override
    protected void createMenuBar(){
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Jeu");
        Menu menu2 = new Menu("Paramètres");
        menu3 = new Menu("Volume de la Musique : " + Sound.getMusicVolume());
        menu4 = new Menu("Volume des Sons : " + Sound.getSoundVolume());
        menuStart = new MenuItem("Nouvelle partie");
        menuIntro = new MenuItem("Retour au menu principal");
        menuQuit = new MenuItem("Quitter");
        menuConfig = new MenuItem("Partie Personnalisée");
        menuMusique = new MenuItem("Basculer la Musique");
        menuSFX = new MenuItem("Basculer les Effets Sonores");
        sLow = new MenuItem("Faible - 25%");
        sMedium = new MenuItem("Moyen - 50%");
        sHigh = new MenuItem("Fort - 75%");
        sMax = new MenuItem("Max - 100%");
        mLow = new MenuItem("Faible - 25%");
        mMedium = new MenuItem("Moyen - 50%");
        mHigh = new MenuItem("Fort - 75%");
        mMax = new MenuItem("Max - 100%");

        menu1.getItems().add(menuStart);
        menu1.getItems().add(menuIntro);
        menu1.getItems().add(menuConfig);
        menu1.getItems().add(menuQuit);

        menu2.getItems().add(menuMusique);
        menu2.getItems().add(menuSFX);
        menu2.getItems().add(menu3);
        menu2.getItems().add(menu4);

        menu3.getItems().addAll(mLow, mMedium, mHigh, mMax);
        menu4.getItems().addAll(sLow, sMedium, sHigh,sMax);

        menuBar.getMenus().addAll(menu1,menu2);

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
        hate1 = new RadioButton("Hate Cards");
        guide1 = new RadioButton("Guide");

        random1.setToggleGroup(groupAI1);
        camarade1.setToggleGroup(groupAI1);
        hate1.setToggleGroup(groupAI1);
        guide1.setToggleGroup(groupAI1);

        ToggleGroup groupAI2 = new ToggleGroup();
        random2 = new RadioButton("Random");
        camarade2 = new RadioButton("Camarade");
        hate2 = new RadioButton("Hate Cards");
        guide2 = new RadioButton("Guide");

        random2.setToggleGroup(groupAI2);
        camarade2.setToggleGroup(groupAI2);
        hate2.setToggleGroup(groupAI2);
        guide2.setToggleGroup(groupAI2);

        bValider = new Button("Lancer la partie");

        // Ajout des éléments au GridPane
        gridPane.add(new Text("Mode de Jeu"), 0, 0);
        gridPane.add(menuHumanVsHuman, 0, 1);
        gridPane.add(menuHumanVsAI, 1, 1);
        gridPane.add(menuAIVsAI, 2, 1);

        gridPane.add(new Text("IA pour le joueur 1"), 0, 3);
        gridPane.add(random1, 0, 4);
        gridPane.add(camarade1, 1, 4);
        gridPane.add(hate1, 2, 4);
        gridPane.add(guide1, 3, 4);

        gridPane.add(new Text("IA pour le joueur 2"), 0, 6);
        gridPane.add(random2, 0, 7);
        gridPane.add(camarade2, 1, 7);
        gridPane.add(hate2, 2, 7);
        gridPane.add(guide2, 3, 7);

        gridPane.add(bValider, 0, 9);

        // Configuring GridPane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        rootPane.getChildren().add(gridPane);


        stage.sizeToScene();

        rootPane.getChildren().remove(0);
        // set the clipping area with the boundaries of root pane.
        Rectangle r = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
        rootPane.setClip(r);
    }

    public MenuItem getMenuMusique() {
        return menuMusique;
    }

    public MenuItem getMenuSFX() {
        return menuSFX;
    }

    public Button getbValider() {
        return bValider;
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

    public MenuItem getsLow() {
        return sLow;
    }

    public MenuItem getsMedium() {
        return sMedium;
    }

    public MenuItem getsHigh() {
        return sHigh;
    }

    public MenuItem getsMax() {
        return sMax;
    }

    public MenuItem getmLow() {
        return mLow;
    }

    public MenuItem getmMedium() {
        return mMedium;
    }

    public MenuItem getmHigh() {
        return mHigh;
    }

    public MenuItem getmMax() {
        return mMax;
    }

    public Menu getMenu3() {
        return menu3;
    }

    public Menu getMenu4() {
        return menu4;
    }
}
