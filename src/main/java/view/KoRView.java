package view;

import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.GameConfigurationModel;

public class KoRView extends View {

    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    private RadioMenuItem menuHumanVsHuman;
    private RadioMenuItem menuHumanVsAI;
    private RadioMenuItem menuAIVsAI;

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
        ToggleGroup group = new ToggleGroup();
        menuHumanVsHuman = new RadioMenuItem("Humain vs Humain");
        menuHumanVsAI = new RadioMenuItem("Humain vs IA");
        menuAIVsAI = new RadioMenuItem("IA vs IA");

        menuHumanVsHuman.setToggleGroup(group);
        menuHumanVsAI.setToggleGroup(group);
        menuAIVsAI.setToggleGroup(group);

        menuConfig.getItems().addAll(menuHumanVsHuman, menuHumanVsAI, menuAIVsAI);

        menuBar.getMenus().addAll(menu1, menuConfig);
    }

    public RadioMenuItem getMenuHumanVsHuman() {
        return menuHumanVsHuman;
    }

    public RadioMenuItem getMenuHumanVsAI() {
        return menuHumanVsAI;
    }

    public RadioMenuItem getMenuAIVsAI() {
        return menuAIVsAI;
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
