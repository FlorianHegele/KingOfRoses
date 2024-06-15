package model;

import boardifier.model.Model;
import control.Sound;
import model.data.AIData;
import model.data.PlayerData;
import model.element.Pawn;
import utils.ContainerElements;

/**
 * The GameStatistic class is responsible for gathering and displaying statistics
 * for a series of AI-driven games in the King of Roses game. It tracks various
 * metrics.
 */
public class GameStatistic {

    private final int gameNumber;
    private double gameBlocked;
    private double gameBlockedRate;

    private final AIStatistic blueStatistic;
    private final AIStatistic redStatistic;

    /**
     * Constructs a GameStatistic instance with the specified AI data and number of games.
     *
     * @param blueAIData The AI for the blue player.
     * @param redAIData  The AI for the red player.
     * @param gameNumber The total number of games to be played.
     */
    public GameStatistic(AIData blueAIData, AIData redAIData, int gameNumber) {
        this.gameNumber = gameNumber;
        this.gameBlocked = 0;

        this.blueStatistic = new AIStatistic(PlayerData.PLAYER_BLUE, blueAIData);
        this.redStatistic = new AIStatistic(PlayerData.PLAYER_RED, redAIData);
    }

    /**
     * Increments the count of blocked games (games that ended in a tie).
     */
    public void incrementGameBlocked() {
        this.gameBlocked++;
    }

    /**
     * Adds statistics from a completed game to the overall statistics.
     *
     * @param model The model of the completed game.
     */
    public void addStatistic(Model model) {
        final KoRStageModel stageModel = (KoRStageModel) model.getGameStage();

        final PlayerData playerData = PlayerData.getPlayerData(model.getIdWinner());
        if (playerData == PlayerData.NONE) {
            incrementGameBlocked();
        } else if (playerData == PlayerData.PLAYER_BLUE) {
            blueStatistic.incrementWin();
        } else if (playerData == PlayerData.PLAYER_RED) {
            redStatistic.incrementWin();
        } else if (playerData == null) {
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

        blueStatistic.incrementAverageZoneSize(stageModel.getZoneAverage(PlayerData.PLAYER_BLUE));
        redStatistic.incrementAverageZoneSize(stageModel.getZoneAverage(PlayerData.PLAYER_RED));

        System.out.println();

        System.out.println("Winner : " + playerData.name().toLowerCase());
    }

    /**
     * Prints the gathered statistics to the console.
     */
    public void printStatistics() {
        System.out.println("Game Number: " + this.gameNumber);

        System.out.println("Tie :");
        System.out.println("| Total tie: " + gameBlocked);
        System.out.println("| Tie rate: " + gameBlockedRate + "%");

        System.out.println();
        blueStatistic.printStatistics();
        System.out.println();
        redStatistic.printStatistics();
    }

    public void calculateStatistic() {
        gameBlockedRate = 100 * gameBlocked / gameNumber;

        blueStatistic.calculateStatistics(gameNumber);
        redStatistic.calculateStatistics(gameNumber);
    }

    public AIStatistic getBlueStatistic() {
        return blueStatistic;
    }

    public AIStatistic getRedStatistic() {
        return redStatistic;
    }

    public double getGameBlocked() {
        return gameBlocked;
    }

    public double getGameBlockedRate() {
        return gameBlockedRate;
    }

    /**
     * The AIStatistic class is responsible for tracking and displaying statistics
     * for a single AI. It tracks metrics.
     */
    public static class AIStatistic {

        private final String description;

        private double winRate;
        private double heroCardUsedRate;
        private double pawnPlayRate;
        private double pointRate;
        private double zoneRate;
        private double averageZoneSizeRate;

        private int totalWin;
        private int totalHeroCardUsed;
        private int totalPawnPlay;
        private int totalPoint;
        private int totalZone;
        private double averageZoneSize;

        /**
         * Constructs an AIStatistic instance with the specified player and AI.
         *
         * @param playerData The player associated to AI.
         * @param aiData     The AI associated to the player.
         */
        public AIStatistic(PlayerData playerData, AIData aiData) {
            this.description = "AI " + (playerData.getId() + 1) + " (" + aiData.name().toLowerCase() + ")";
        }

        /**
         * Increments the win count for the AI.
         */
        public void incrementWin() {
            this.totalWin++;
        }

        /**
         * Increments the count of hero cards used by the AI.
         *
         * @param heroCardUsed The number of hero cards used in the current game.
         */
        public void incrementHeroCardUsed(int heroCardUsed) {
            this.totalHeroCardUsed += heroCardUsed;
        }

        /**
         * Increments the count of pawns played by the AI.
         *
         * @param pawns The number of pawns played in the current game.
         */
        public void incrementPawnPlay(int pawns) {
            this.totalPawnPlay += pawns;
        }

        /**
         * Increments the total points scored by the AI.
         *
         * @param points The points scored in the current game.
         */
        public void incrementPoint(int points) {
            this.totalPoint += points;
        }

        /**
         * Increments the count of zones controlled by the AI.
         *
         * @param zone The number of zones controlled in the current game.
         */
        public void incrementZone(int zone) {
            this.totalZone += zone;
        }

        /**
         * Increments the total size of zones controlled by the AI.
         *
         * @param averageZoneSize The average size of zones controlled in the current game.
         */
        public void incrementAverageZoneSize(double averageZoneSize) {
            this.averageZoneSize += averageZoneSize;
        }

        /**
         * Prints the AI statistics to the console.
         */
        private void printStatistics() {
            System.out.println();
            System.out.println(description);

            System.out.println("| Total win: " + totalWin);
            System.out.println("| Win rate: " + winRate + "%");

            System.out.println("-".repeat(20));

            System.out.println("| Total Hero card: " + totalHeroCardUsed);
            System.out.println("| Hero card rate: " + heroCardUsedRate);

            System.out.println("-".repeat(20));

            System.out.println("| Total Pawn played: " + totalPawnPlay);
            System.out.println("| Pawn played average : " + pawnPlayRate);

            System.out.println("-".repeat(20));

            System.out.println("| Total Point: " + totalPoint);
            System.out.println("| Point average : " + pointRate);

            System.out.println("-".repeat(20));

            System.out.println("| Total Zone: " + totalZone);
            System.out.println("| Zone average : " + zoneRate);

            System.out.println("-".repeat(20));

            System.out.println("| Average Zone Size: " + averageZoneSize);
            System.out.println("| Average Zone Size Per Game: " + averageZoneSizeRate);
        }

        /**
         * Calculate the AI statistics.
         *
         * @param totalGame The total number of games played.
         */
        private void calculateStatistics(double totalGame) {
            winRate = 100 * totalWin / totalGame;

            heroCardUsedRate = totalHeroCardUsed / totalGame;

            pawnPlayRate = totalPawnPlay / totalGame;

            pointRate = totalPoint / totalGame;

            zoneRate = totalZone / totalGame;

            averageZoneSizeRate = averageZoneSize / totalGame;
        }

        public double getAverageZoneSizeRate() {
            return averageZoneSizeRate;
        }

        public double getHeroCardUsedRate() {
            return heroCardUsedRate;
        }

        public double getPointRate() {
            return pointRate;
        }

        public double getZoneRate() {
            return zoneRate;
        }

        public double getPawnPlayRate() {
            return pawnPlayRate;
        }

        public double getWinRate() {
            return winRate;
        }

        public double getAverageZoneSize() {
            return averageZoneSize;
        }

        public int getTotalZone() {
            return totalZone;
        }

        public int getTotalPoint() {
            return totalPoint;
        }

        public int getTotalPawnPlay() {
            return totalPawnPlay;
        }

        public int getTotalHeroCardUsed() {
            return totalHeroCardUsed;
        }

        public int getTotalWin() {
            return totalWin;
        }
    }

}
