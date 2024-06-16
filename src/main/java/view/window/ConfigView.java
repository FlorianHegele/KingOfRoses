package view.window;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * View class for the configuration window, extending WindowView with a GridPane.
 */
public class ConfigView extends WindowView<GridPane> {

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

    private Button bValider;

    /**
     * Constructs a ConfigView with a new GridPane.
     */
    public ConfigView() {
        super(new GridPane());

        // Configuring pane
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Creates the default pane for the configuration view, adding all necessary components.
     */
    @Override
    public void createDefaultPane() {
        ToggleGroup groupPlayers = new ToggleGroup();
        menuHumanVsHuman = new RadioButton("Humain vs Humain");
        menuHumanVsAI = new RadioButton("Humain vs IA");
        menuAIVsAI = new RadioButton("IA vs IA");

        menuHumanVsHuman.setToggleGroup(groupPlayers);
        menuHumanVsAI.setToggleGroup(groupPlayers);
        menuAIVsAI.setToggleGroup(groupPlayers);

        ToggleGroup groupAI1 = new ToggleGroup();
        random1 = new RadioButton("Random (Joue au pif)");
        camarade1 = new RadioButton("Camarade (Amicale)");
        hate1 = new RadioButton("Hate Cards (Rapide)");
        guide1 = new RadioButton("Guide (Aggressive)");

        random1.setToggleGroup(groupAI1);
        camarade1.setToggleGroup(groupAI1);
        hate1.setToggleGroup(groupAI1);
        guide1.setToggleGroup(groupAI1);

        ToggleGroup groupAI2 = new ToggleGroup();
        random2 = new RadioButton("Random (Joue au pif)");
        camarade2 = new RadioButton("Camarade (Amicale)");
        hate2 = new RadioButton("Hate Cards (Rapide)");
        guide2 = new RadioButton("Guide (Aggressive)");

        random2.setToggleGroup(groupAI2);
        camarade2.setToggleGroup(groupAI2);
        hate2.setToggleGroup(groupAI2);
        guide2.setToggleGroup(groupAI2);

        bValider = new Button("Lancer la partie");

        // Ajout des éléments au GridPane
        pane.add(new Text("Mode de Jeu"), 0, 0);
        pane.add(menuHumanVsHuman, 0, 1);
        pane.add(menuHumanVsAI, 1, 1);
        pane.add(menuAIVsAI, 2, 1);

        pane.add(new Text("IA pour le joueur 1"), 0, 3);
        pane.add(random1, 0, 4);
        pane.add(camarade1, 1, 4);
        pane.add(hate1, 2, 4);
        pane.add(guide1, 3, 4);

        pane.add(new Text("IA pour le joueur 2"), 0, 6);
        pane.add(random2, 0, 7);
        pane.add(camarade2, 1, 7);
        pane.add(hate2, 2, 7);
        pane.add(guide2, 3, 7);

        pane.add(bValider, 0, 9);
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

}
