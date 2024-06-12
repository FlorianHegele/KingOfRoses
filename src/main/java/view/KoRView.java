package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class KoRView extends View {

    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;

    public KoRView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    @Override
    protected void createMenuBar() {

        menuBar = new MenuBar();
        Menu menu1 = new Menu("Jeu");
        menuStart = new MenuItem("Nouvelle partie");
        menuIntro = new MenuItem("Retour au menu principal");
        menuQuit = new MenuItem("Quitter");

        menu1.getItems().addAll(menuStart, menuIntro, menuQuit);

        Menu menuConfig = new Menu("Configuration");
        Menu menuPlayers = new Menu("Choix des Joueurs");
        Menu menuAI = new Menu("Choix des IA");
        Menu menuAI1 = new Menu("IA pour le joueur 1");
        Menu menuAI2 = new Menu("IA pour le joueur 2");

        ToggleGroup groupPlayers = new ToggleGroup();
        RadioMenuItem menuHumanVsHuman = new RadioMenuItem("Humain vs Humain");
        RadioMenuItem menuHumanVsAI = new RadioMenuItem("Humain vs IA");
        RadioMenuItem menuAIVsAI = new RadioMenuItem("IA vs IA");

        ToggleGroup groupAI1 = new ToggleGroup();
        RadioMenuItem random1 = new RadioMenuItem("Random");
        RadioMenuItem camarade1 = new RadioMenuItem("Camarade");
        RadioMenuItem hate1 = new RadioMenuItem("Hate");
        RadioMenuItem guide1 = new RadioMenuItem("Guide");

        ToggleGroup groupAI2 = new ToggleGroup();
        RadioMenuItem random2 = new RadioMenuItem("Random");
        RadioMenuItem camarade2 = new RadioMenuItem("Camarade");
        RadioMenuItem hate2 = new RadioMenuItem("Hate");
        RadioMenuItem guide2 = new RadioMenuItem("Guide");

        menuHumanVsHuman.setToggleGroup(groupPlayers);
        menuHumanVsAI.setToggleGroup(groupPlayers);
        menuAIVsAI.setToggleGroup(groupPlayers);

        random1.setToggleGroup(groupAI1);
        random2.setToggleGroup(groupAI2);
        camarade1.setToggleGroup(groupAI1);
        camarade2.setToggleGroup(groupAI2);
        hate1.setToggleGroup(groupAI1);
        hate2.setToggleGroup(groupAI2);
        guide1.setToggleGroup(groupAI1);
        guide2.setToggleGroup(groupAI2);

        menuConfig.getItems().addAll(menuPlayers, menuAI);
        menuPlayers.getItems().addAll(menuHumanVsHuman, menuHumanVsAI, menuAIVsAI);
        menuAI.getItems().addAll(menuAI1, menuAI2);
        menuAI1.getItems().addAll(random1, camarade1, hate1, guide1);
        menuAI2.getItems().addAll(random2, camarade2, hate2, guide2);

        menuBar.getMenus().addAll(menu1, menuConfig);
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
