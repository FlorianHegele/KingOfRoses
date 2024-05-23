package model;

import boardifier.model.Model;
import model.data.AIData;
import model.data.PlayerData;
import model.element.Pawn;
import utils.ContainerElements;

public class GameStatistic {

    private final int gameNumber;
    private double gameBlocked;

    private final AIStatistic blueStatistic;
    private final AIStatistic redStatistic;

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

        final int blueHeroCardUsed = 4 - ContainerElements.countElements(stageModel.getBlueHeroCardStack());
        final int redHeroCardUsed = 4 - ContainerElements.countElements(stageModel.getRedHeroCardStack());
        blueStatistic.incrementHeroCardUsed(blueHeroCardUsed);
        redStatistic.incrementHeroCardUsed(redHeroCardUsed);

        blueStatistic.incrementPawnPlay(stageModel.getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN) - blueHeroCardUsed);
        redStatistic.incrementPawnPlay(stageModel.getTotalPawnOnBoard(Pawn.Status.RED_PAWN) - redHeroCardUsed);

        blueStatistic.incrementPoint(stageModel.getTotalPlayerPointSimple(PlayerData.PLAYER_BLUE));
        redStatistic.incrementPoint(stageModel.getTotalPlayerPointSimple(PlayerData.PLAYER_RED));

        blueStatistic.incrementZone(stageModel.getTotalPawnZone(PlayerData.PLAYER_BLUE));
        redStatistic.incrementZone(stageModel.getTotalPawnZone(PlayerData.PLAYER_RED));

        System.out.println("Winner : " + playerData.name().toLowerCase());
    }

    public void printStatistics() {
        System.out.println("Game Number: " + this.gameNumber);

        System.out.println("Tie :");
        System.out.println("| Total tie: " + gameBlocked);
        System.out.println("| Tie rate: " + (100 * gameBlocked / gameNumber)+"%");

        System.out.println();
        blueStatistic.printStatistics(gameNumber);
        System.out.println();
        redStatistic.printStatistics(gameNumber);
    }

    public static class AIStatistic {

        private final PlayerData playerData;
        private final AIData ai;
        private double totalWin;
        private double totalHeroCardUsed;
        private double totalPawnPlay;
        private double totalPoint;
        private double totalZone;

        public AIStatistic(PlayerData playerData, AIData aiData) {
            this.playerData = playerData;
            this.ai = aiData;
        }

        public void incrementWin() {
            this.totalWin++;
        }

        public void incrementHeroCardUsed(int heroCardUsed) {
            this.totalHeroCardUsed += heroCardUsed;
        }

        public void incrementPawnPlay(int pawns) {
            this.totalPawnPlay += pawns;
        }

        public void incrementPoint(int points) {
            this.totalPoint += points;
        }

        public void incrementZone(int zone) {
            this.totalZone += zone;
        }

        private void printStatistics(int totalGame) {
            System.out.println();
            System.out.println("AI "+(playerData.getId()+1)+" ("+ai.name().toLowerCase()+")");
            System.out.println("| Total win: " + totalWin);
            System.out.println("| Win rate: " + (100 * totalWin / totalGame)+"%");
            System.out.println("-".repeat(20));
            System.out.println("| Total Hero card: " + totalHeroCardUsed);
            System.out.println("| Hero card rate: " + (25 * totalHeroCardUsed / totalGame)+"%");
            System.out.println("-".repeat(20));
            System.out.println("| Total Pawn played: " + totalPawnPlay);
            System.out.println("| Pawn played average : " + (totalPawnPlay / totalGame));
            System.out.println("-".repeat(20));
            System.out.println("| Total Point: " + totalPoint);
            System.out.println("| Point average : " + (totalPoint / totalGame));
            System.out.println("-".repeat(20));
            System.out.println("| Total Zone: " + totalZone);
            System.out.println("| Zone average : " + (totalZone / totalGame));
        }
    }

}
