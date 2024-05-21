package model.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerDataTest {

    @Test
    void testGetNextPlayer() {
        final PlayerData redPlayer = PlayerData.PLAYER_RED;
        final PlayerData bluePlayer = PlayerData.PLAYER_BLUE;

        assertEquals(bluePlayer, redPlayer.getNextPlayerData());
        assertEquals(redPlayer, bluePlayer.getNextPlayerData());
    }

}
