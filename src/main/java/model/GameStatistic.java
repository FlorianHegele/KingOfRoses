package model;

import boardifier.model.Model;
import model.container.card.HeroCardStack;
import model.data.AIData;
import model.data.PlayerData;
import utils.ContainerElements;

public class GameStatistic {

    private final int gameNumber;
    private int gameBlocked;

    private AIStatistic blueStatistic;
    private AIStatistic redStatistic;

    public GameStatistic(AIData blueAIData, AIData redAIData, int gameNumber) {
        this.gameNumber = gameNumber;
        this.gameBlocked = 0;

        this.blueStatistic = new AIStatistic(PlayerData.PLAYER_BLUE, blueAIData);
        this.redStatistic = new AIStatistic(PlayerData.PLAYER_RED, redAIData);
    }

    public void incrementGameBlocked() {
        this.gameBlocked++;
    }

    public void addStatistic(Model model) {
        final KoRStageModel stageModel = (KoRStageModel) model.getGameStage();

        final PlayerData playerData = PlayerData.getPlayerData(model.getIdWinner());
        if(playerData == PlayerData.NONE) {
            incrementGameBlocked();
        } else if (playerData == PlayerData.PLAYER_BLUE) {
            blueStatistic.incrementWin();
        } else if (playerData == PlayerData.PLAYER_RED) {
            redStatistic.incrementWin();
        } else if(playerData == null) {
            throw new IllegalArgumentException("Player data is null");
        }

        blueStatistic.incrementHeroCardUsed(stageModel.getBlueHeroCardStack());
        redStatistic.incrementHeroCardUsed(stageModel.getRedHeroCardStack());

        System.out.println("Winner : " + playerData.name().toLowerCase());
    }

    public void printStatistics() {
        System.out.println("Game Number: " + this.gameNumber);

        System.out.println("Tie :");
        System.out.println("| Total tie: " + gameBlocked);
        System.out.println("| Tie rate: " + (100 * gameBlocked / gameNumber)+"%");

        blueStatistic.printStatistics(gameNumber);
        redStatistic.printStatistics(gameNumber);
    }

    public static class AIStatistic {

        private final PlayerData playerData;
        private final AIData ai;
        private int totalWin;
        private int totalHeroCardUsed;
        private int totalPawnUsed;

        public AIStatistic(PlayerData playerData, AIData aiData) {
            this.playerData = playerData;
            this.ai = aiData;

            this.totalWin = 0;
            this.totalHeroCardUsed = 0;
            this.totalPawnUsed = 0;
        }

        public void incrementWin() {
            this.totalWin++;
        }

        public void incrementHeroCardUsed(HeroCardStack heroCardStack) {
            this.totalHeroCardUsed += 4 - ContainerElements.countElements(heroCardStack);
        }

        public void incrementPawnUsed(int pawns) {
            this.totalPawnUsed += 52 - pawns;
        }

        private void printStatistics(int totalGame) {
            System.out.println();
            System.out.println("AI "+(playerData.getId()+1)+" ("+ai.name().toLowerCase()+"):");
            System.out.println("| Total win: " + totalWin);
            System.out.println("| Win rate: " + (100 * totalWin / totalGame)+"%");
            System.out.println("| Total Hero card: " + totalHeroCardUsed);
            System.out.println("| Hero card rate: " + (25 * totalHeroCardUsed / totalGame)+"%");
        }
    }

}
