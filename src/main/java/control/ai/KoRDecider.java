package control.ai;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import control.SimpleActionList;
import model.data.PlayerData;

import java.util.Calendar;
import java.util.Random;

/**
 * Abstract class for KoR decision making in the game.
 * This class serves as a base for different decision-making strategies.
 * It provides common data for its subclasses, such as access to player data and action lists.
 */
public abstract class KoRDecider extends Decider {

    protected static final Random LOTO = new Random(Calendar.getInstance().getTimeInMillis());

    protected final PlayerData playerData;
    protected final SimpleActionList simpleActionList;

    /**
     * Constructs a KoRDecider with the specified model, controller, and player data.
     *
     * @param model the game model.
     * @param control the game controller.
     * @param playerData the player data associated with this decider.
     */
    protected KoRDecider(Model model, Controller control, PlayerData playerData) {
        super(model, control);
        this.playerData = playerData;
        this.simpleActionList = new SimpleActionList(model, playerData);
    }

    /**
     * Returns the player data associated with this decider.
     *
     * @return the player data.
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

}
