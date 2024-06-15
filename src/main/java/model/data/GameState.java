package model.data;

public enum GameState {

    SELECT_NONE(0), // CAN SELECT A MOVEMENT CARD FROM THE STACK OR HIS HAND
    SELECT_MOVEMENT_CARD(1), // CAN SELECT THE DEST OR A HERO CARD OR UNSELECT HIS CARD
    SELECT_MOVEMENT_CARD_HERO(2); // CAN SELECT THE DEST OR UNSELECT THE HERO CARD

    private final int value;

    GameState(int value) {
        this.value = value;
    }

    public static GameState getState(int state) {
        for(GameState gameState : GameState.values()) {
            if(gameState.getValue() == state) {
                return gameState;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

}
