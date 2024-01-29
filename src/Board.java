public class Board {

    // source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private int[][] board = new int[9][9];

    /*
    * Place le roi à la position désirée
    * @param ligne position x du roi de 0 à 8
    * @param colonne position y du roi de 0 à 8
     */
    public void setKing(int ligne, int colonne){
        if (ligne < 0 || ligne > 8 || colonne < 0 || colonne > 8) {
            throw new IllegalArgumentException("Position du roi invalide");
        }
        board[ligne][colonne] = 1;
    }

    /*
     * Place le roi au centre du plateau par défaut
     */
    public void setKing(){
        board[4][4] = 1;
    }
    public String toString(){
        String boardTemplate = "";
        boardTemplate += ANSI_CYAN + "    0   1   2   3   4   5   6   7   8\n" + ANSI_RESET; // Numérotation des colonnes
        for (int i = 0; i < 9; i++) {
                boardTemplate += "  -------------------------------------\n"; // Ligne de séparation
                boardTemplate += ANSI_CYAN + i + " " + ANSI_RESET + "| ";// Bordure gauche et numérotation des lignes

            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 1) { // Si la case contient le roi
                    boardTemplate += ANSI_YELLOW + "K" + ANSI_RESET + " | "; // Colore le roi en jaune
                    continue;
                }
                boardTemplate += board[i][j] + " | "; // Affiche la valeur de la case
            }
            boardTemplate += "\n"; // Retour à la ligne après chaque ligne du tableau
        }
        boardTemplate += "  -------------------------------------\n"; // Dernière ligne du plateau
        return boardTemplate;
    }

}
