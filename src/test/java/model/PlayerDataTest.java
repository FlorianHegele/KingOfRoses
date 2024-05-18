package model;

import model.data.PlayerData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerDataTest {

    @Test
    public void testGetNextPlayer() {
        PlayerData redPlayer = PlayerData.PLAYER_RED;
        PlayerData bluePlayer = PlayerData.PLAYER_BLUE;

        assertEquals(bluePlayer, redPlayer.getNextPlayerData());
        assertEquals(redPlayer, bluePlayer.getNextPlayerData());
    }

}
