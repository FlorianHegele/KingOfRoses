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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.data.WindowType;
import view.window.WindowView;

import java.util.*;

public class KoRView extends View {

    private final Map<WindowType, WindowView> windows = new EnumMap<>(WindowType.class);
    private WindowType currentWindow;

    private MenuItem menuConfig;
    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    private MenuItem menuMusique;
    private MenuItem menuSFX;

    public KoRView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
        currentWindow = WindowType.NONE;
        createWindows();
    }

    @Override
    protected void createMenuBar(){
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Jeu");
        Menu menu2 = new Menu("Paramètres");
        menuStart = new MenuItem("Nouvelle partie");
        menuIntro = new MenuItem("Retour au menu principal");
        menuQuit = new MenuItem("Quitter");
        menuConfig = new MenuItem("Partie Personnalisée");
        menuMusique = new MenuItem("Basculer la Musique");
        menuSFX = new MenuItem("Basculer les Effets Sonores");

        menu1.getItems().add(menuStart);
        menu1.getItems().add(menuIntro);
        menu1.getItems().add(menuConfig);
        menu1.getItems().add(menuQuit);

        menu2.getItems().add(menuMusique);
        menu2.getItems().add(menuSFX);

        menuBar.getMenus().addAll(menu1,menu2);
    }

    public WindowView setContent(WindowType windowType) {
        if(this.currentWindow == windowType) return windows.get(currentWindow);
        resetView();
        this.currentWindow = windowType;

        WindowView windowView = windows.get(currentWindow);
        rootPane.getChildren().add(windowView.getPane());

        Rectangle r;
        if(windowView.sizeToScene()) {
            stage.sizeToScene();
            r = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
        } else {
            stage.setWidth(windowView.getWidth());
            stage.setHeight(windowView.getHeight());
            r = new Rectangle(windowView.getWidth(), windowView.getHeight());
        }

        rootPane.getChildren().remove(0);

        // set the clipping area with the boundaries of root pane.

        rootPane.setClip(r);

        return windowView;
    }

    public WindowView getCurrentWindow() {
        return windows.get(currentWindow);
    }

    public WindowType getCurrentWindowType() {
        return currentWindow;
    }

    @Override
    public void resetView() {
        super.resetView();
        this.currentWindow = WindowType.NONE;
    }

    public MenuItem getMenuMusique() {
        return menuMusique;
    }

    public MenuItem getMenuSFX() {
        return menuSFX;
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

    public void createWindows() {
        for (WindowType windowType : WindowType.values()) {
            if(windowType == WindowType.NONE) continue;
            try {
                final WindowView windowView = windowType.generateWindow();
                windowView.createDefaultPane();
                windows.put(windowType, windowView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public WindowView getWindow(WindowType windowType) {
        return windows.get(windowType);
    }
}
